package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class LoginCLIPrompt extends BaseCLIPrompt {
  private final LoginUseCase loginUseCase;

  public LoginCLIPrompt(BaseCLIPrompt previousPrompt, LoginUseCase loginUseCase) {
    super(previousPrompt);
    this.loginUseCase = loginUseCase;
  }

  @Override
  public void start() {
    super.start();

    String loginEmailInput = simplePrompt("What is your email?");
    String loginPasswordInput = simplePrompt("What is your password?");

    try {
      loginUseCase.login(Address.from(loginEmailInput), loginPasswordInput);
    } catch (AuthorizationException
        | SaveUserException
        | LoadingUsersException
        | IllegalArgumentException e) {
      printWarning(e.getMessage());
    }
  }
}
