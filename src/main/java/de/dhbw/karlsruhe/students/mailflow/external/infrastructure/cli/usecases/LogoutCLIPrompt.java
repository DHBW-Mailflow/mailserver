package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AbstractCLIPrompt;

/**
 * @author Jonas-Karl
 */
public class LogoutCLIPrompt extends AbstractCLIPrompt {

  private final AuthUseCase authUseCase;

  public LogoutCLIPrompt(AuthUseCase authUseCase) {
    this.authUseCase = authUseCase;
  }

  @Override
  public void start() {
    super.start();

    User user = authUseCase.logout();
    printDefault("Good bye, %s!".formatted(user.email()));
    stop();
  }
}
