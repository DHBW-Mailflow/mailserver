package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seiferla
 */
public abstract class ShowEmailsCLIPrompt extends AuthorizedCLIPrompt {

  final ProvideEmailsUseCase provideEmailsUseCase;
  final MailboxType mailboxType;

  protected ShowEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      ProvideEmailsUseCase provideEmailsUseCase,
      MailboxType mailboxType) {
    super(previousPrompt, authUseCase);
    this.provideEmailsUseCase = provideEmailsUseCase;
    this.mailboxType = mailboxType;
  }

  public String formatEmail(Email email) {
    return "%s: %s - %s"
        .formatted(
            email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  public BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    if (emailList.isEmpty()) {
      printDefault("No emails found");
      return getPreviousPrompt();
    }
    printDefault("Which email do you want to read?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(
          formatEmail(email),
          new ShowEmailContentCLIPrompt(
              this, email, provideEmailsUseCase, authUseCase, mailboxType));
    }
    return readUserInputWithOptions(promptMap);
  }
}
