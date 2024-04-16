package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seiferla
 */
public abstract class ShowEmailsCLIPrompt extends BaseCLIPrompt {

  final AuthUseCase authUseCase;
  final ProvideEmailsUseCase provideEmailsUseCase;
  final MailboxType mailboxType;

  protected ShowEmailsCLIPrompt(AuthUseCase authUseCase, ProvideEmailsUseCase provideEmailsUseCase,
      MailboxType mailboxType) {
    this.authUseCase = authUseCase;
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
