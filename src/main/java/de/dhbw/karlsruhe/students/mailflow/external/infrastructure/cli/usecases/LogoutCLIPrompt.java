package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class LogoutCLIPrompt extends AuthorizedCLIPrompt {

  public LogoutCLIPrompt(BaseCLIPrompt previousPrompt, AuthUseCase authUseCase) {
    super(previousPrompt, authUseCase);
  }

  @Override
  public void start() {
    super.start();

    Address user = authUseCase.logout();
    printDefault("Good bye, %s!".formatted(user));
  }
}
