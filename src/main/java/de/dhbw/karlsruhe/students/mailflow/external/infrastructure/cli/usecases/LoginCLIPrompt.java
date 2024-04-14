package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AbstractCLIPrompt;

public class LoginCLIPrompt extends AbstractCLIPrompt {
    private final LoginUseCase loginUseCase;

    public LoginCLIPrompt(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

  public void start() {
    String loginEmailInput = simplePrompt("What is your email?");
    String loginPasswordInput = simplePrompt("What is your password?");

    try {
      loginUseCase.login(loginEmailInput, loginPasswordInput);
    } catch (AuthorizationException | LoadingUsersException e) {
      System.err.println(e.getMessage());
    }
  }
}
