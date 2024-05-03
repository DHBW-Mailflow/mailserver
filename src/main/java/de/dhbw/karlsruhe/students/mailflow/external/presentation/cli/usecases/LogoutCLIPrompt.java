package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LogoutUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

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

    Address userEmail = logoutUseCase.logout();
    printDefault("Good bye, %s!".formatted(userEmail.toString()));
  }
}
