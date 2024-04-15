package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class LoginCLIPrompt extends BaseCLIPrompt {
  private final AuthUseCase authUseCase;

  public LoginCLIPrompt(AuthUseCase authUseCase) {
    this.authUseCase = authUseCase;
  }

  @Override
  public void start() {
    super.start();

    String loginEmailInput = simplePrompt("What is your email?");
    String loginPasswordInput = simplePrompt("What is your password?");

    try {
      authUseCase.login(loginEmailInput, loginPasswordInput);
    } catch (AuthorizationException | LoadingUsersException e) {
      printWarning(e.getMessage());
    }
  }
}
