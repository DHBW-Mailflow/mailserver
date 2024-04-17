package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
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

  private final ProvideEmailsUseCase provideEmailsUseCase;

  ShowEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      ProvideEmailsUseCase provideEmailsUseCase) {
    super(previousPrompt, authUseCase);
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public void start() {
    super.start();
    String mailboxString = provideEmailsUseCase.getMailboxName();
    printDefault("This are your %s emails:".formatted(mailboxString));

    try {
      List<Email> emailList =
          provideEmailsUseCase.provideEmails(authUseCase.getSessionUserAddress());
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not read %s emails".formatted(mailboxString));
    }
  }

  private String formatEmail(Email email) {
    return "%s: %s - %s"
        .formatted(
            email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    if (emailList.isEmpty()) {
      printDefault("No emails found");
      return getPreviousPrompt();
    }
    printDefault("Which email do you want to read?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(
          formatEmail(email),
          new ShowEmailContentCLIPrompt(this, email, provideEmailsUseCase, authUseCase));
    }
    return readUserInputWithOptions(promptMap);
  }
}
