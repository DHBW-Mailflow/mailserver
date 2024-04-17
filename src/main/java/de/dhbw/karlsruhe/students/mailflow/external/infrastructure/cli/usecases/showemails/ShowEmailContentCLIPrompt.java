package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author seiferla
 */
public class ShowEmailContentCLIPrompt extends AuthorizedCLIPrompt {

  private final Email email;

  private final ProvideEmailsUseCase provideEmailsUseCase;

  public ShowEmailContentCLIPrompt(
      BaseCLIPrompt previousPrompt,
      Email email,
      ProvideEmailsUseCase provideEmailsUseCase,
      AuthUseCase authUseCase) {
    super(previousPrompt, authUseCase);
    this.email = email;
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public void start() {
    try {
      provideEmailsUseCase.markEmailAsRead(email, authUseCase.getSessionUserAddress());
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not mark email as read");
    }
    printDefault(formatEmailContent(email));
  }

  public String formatEmailContent(Email email) {
    return email.getContent();
  }
}
