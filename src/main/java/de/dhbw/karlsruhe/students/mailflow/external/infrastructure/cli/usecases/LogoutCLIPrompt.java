package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class LogoutCLIPrompt extends BaseCLIPrompt {

  private final AuthUseCase authUseCase;

  public LogoutCLIPrompt(AuthUseCase authUseCase) {
    this.authUseCase = authUseCase;
  }

  @Override
  public void start() throws MailboxSavingException, MailboxLoadingException {
    super.start();

    User user = authUseCase.logout();
    printDefault("Good bye, %s!".formatted(user.email()));
    stop();
  }
}
