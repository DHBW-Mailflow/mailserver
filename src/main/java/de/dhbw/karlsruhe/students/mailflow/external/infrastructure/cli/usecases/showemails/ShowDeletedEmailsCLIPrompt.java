package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.List;

/**
 * @author seiferla
 */
public class ShowDeletedEmailsCLIPrompt extends ShowEmailsCLIPrompt {

  public ShowDeletedEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      ProvideEmailsUseCase provideEmailsUseCase,
      MailboxType mailboxType) {
    super(previousPrompt, authUseCase, provideEmailsUseCase, mailboxType);
  }

  @Override
  public void start() {
    super.start();
    printDefault("Deleted Emails:");

    try {
      List<Email> emailList =
          provideEmailsUseCase.provideDeletedEmails(authUseCase.getSessionUserAddress());
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not read deleted emails");
    }
  }
}
