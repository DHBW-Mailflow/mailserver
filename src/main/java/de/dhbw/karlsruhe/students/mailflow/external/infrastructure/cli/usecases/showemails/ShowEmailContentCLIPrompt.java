package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author seiferla
 */
public class ShowEmailContentCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  private final ProvideEmailsUseCase provideEmailsUseCase;

  private final AuthUseCase authUseCase;

  public ShowEmailContentCLIPrompt(
      Email email, ProvideEmailsUseCase provideEmailsUseCase, AuthUseCase authUseCase) {
    this.email = email;
    this.provideEmailsUseCase = provideEmailsUseCase;
    this.authUseCase = authUseCase;
  }

  @Override
  public void start() {
    try {
      provideEmailsUseCase.markEmailAsRead(email, authUseCase.getSessionUser().email());
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not mark email as read");
    }
    printDefault(formatEmailContent(email));
  }

  public String formatEmailContent(Email email) {
    return "%s".formatted(email.getContent());
  }
}
