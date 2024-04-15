package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.List;

/**
 * @author seiferla
 */
public class ShowReadInboxEmailsCLIPrompt extends ShowEmailsCLIPrompt {

  public ShowReadInboxEmailsCLIPrompt(
      AuthUseCase authUseCase, ProvideEmailsUseCase provideEmailsUseCase) {
    super(authUseCase, provideEmailsUseCase);
  }

  @Override
  public void start() {
    super.start();
    printDefault("Read Emails:");

    try {
      List<Email> emailList =
          provideEmailsUseCase.provideReadEmails(authUseCase.getSessionUser().email());
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not read inbox emails");
    }
  }
}
