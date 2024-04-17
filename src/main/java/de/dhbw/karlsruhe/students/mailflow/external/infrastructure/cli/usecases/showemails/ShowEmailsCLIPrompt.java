package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seiferla
 */
public class ShowEmailsCLIPrompt extends AuthorizedCLIPrompt {

  final ProvideEmailsUseCase provideEmailsUseCase;
  final MailboxType mailboxType;
  final Label[] labels;

  private ShowEmailsCLIPrompt(
      AuthUseCase authUseCase,
      ProvideEmailsUseCase provideEmailsUseCase,
      MailboxType mailboxType,
      Label... labels) {
    super(authUseCase);
    this.provideEmailsUseCase = provideEmailsUseCase;
    this.mailboxType = mailboxType;
    this.labels = labels;
  }

  public static ShowEmailsCLIPrompt unreadInbox(
      AuthUseCase authUseCase, ProvideEmailsUseCase provideEmailsUseCase) {
    return new ShowEmailsCLIPrompt(
        authUseCase, provideEmailsUseCase, MailboxType.INBOX, Label.UNREAD);
  }

  public static ShowEmailsCLIPrompt readInbox(
      AuthUseCase authUseCase, ProvideEmailsUseCase provideEmailsUseCase) {
    return new ShowEmailsCLIPrompt(
        authUseCase, provideEmailsUseCase, MailboxType.INBOX, Label.READ);
  }

  public static ShowEmailsCLIPrompt ofType(
      AuthUseCase authUseCase, ProvideEmailsUseCase provideEmailsUseCase, MailboxType mailboxType) {
    return new ShowEmailsCLIPrompt(
        authUseCase, provideEmailsUseCase, mailboxType, Label.READ, Label.UNREAD);
  }

  @Override
  public void start() {
    super.start();
    String mailboxString = mailboxType.getStoringName();
    printDefault("This are your %s emails:".formatted(mailboxString));

    try {
      List<Email> emailList =
          provideEmailsUseCase.provideEmails(
              authUseCase.getSessionUserAddress(), mailboxType, labels);
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not read %s emails".formatted(mailboxString));
    }
  }

  public String formatEmail(Email email) {
    return "%s: %s - %s"
        .formatted(
            email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  public BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    if (emailList.isEmpty()) {
      printWarning("No emails found");
      return new ShowEmailTypesCLIPrompt(authUseCase, provideEmailsUseCase);
    }
    printDefault("Which email do you want to read?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(
          formatEmail(email),
          new ShowEmailContentCLIPrompt(email, provideEmailsUseCase, authUseCase, mailboxType));
    }
    return readUserInputWithOptions(promptMap);
  }
}
