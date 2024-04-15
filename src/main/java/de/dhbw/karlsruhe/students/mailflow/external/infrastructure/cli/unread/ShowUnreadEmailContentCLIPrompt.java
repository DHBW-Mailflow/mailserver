package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.unread;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class ShowUnreadEmailContentCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  private final ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase;

  private final AuthUseCase authUseCase;

  public ShowUnreadEmailContentCLIPrompt(
      Email email, ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase, AuthUseCase authUseCase) {
    this.email = email;
    this.provideUnreadEmailsUseCase = provideUnreadEmailsUseCase;
    this.authUseCase = authUseCase;
  }

  @Override
  public void start() {
    try {
      provideUnreadEmailsUseCase.markEmailAsRead(email, authUseCase.getSessionUser().email());
    } catch (MailboxSavingException | MailboxLoadingException e) {
      throw new RuntimeException(e);
    }
    printDefault(formatEmailContent(email));
  }

  public String formatEmailContent(Email email) {
    return "%s".formatted(email.getContent());
  }
}
