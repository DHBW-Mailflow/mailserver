package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LogoutUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class LogoutCLIPrompt extends BaseCLIPrompt {

  private final LogoutUseCase logoutUseCase;

  public LogoutCLIPrompt(BaseCLIPrompt previousPrompt, LogoutUseCase logoutUseCase) {
    super(previousPrompt);
    this.logoutUseCase = logoutUseCase;
  }

  @Override
  public void start() {
    super.start();

    Address user = logoutUseCase.logout();
    printDefault("Good bye, %s!".formatted(user));
  }
}
