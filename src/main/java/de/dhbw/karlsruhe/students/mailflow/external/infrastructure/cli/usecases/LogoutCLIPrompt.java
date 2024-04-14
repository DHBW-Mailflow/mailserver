package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AbstractCLIPrompt;

public class LogoutCLIPrompt extends AbstractCLIPrompt {

  private final AuthUseCase authUseCase;

  public LogoutCLIPrompt(AuthUseCase authUseCase) {
    this.authUseCase = authUseCase;
  }

  // TODO remove from LoginUseCase session
  @Override
  public void start() {
    User user = authUseCase.logout();
    System.out.printf("Good bye, %s!", user.email());
    System.out.println("Good bye!");
    System.exit(0);
  }
}
