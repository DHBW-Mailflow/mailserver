package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;

public class LoginCLIPrompt extends AbstractCLIPrompt {
    private final LoginUseCase loginUseCase;

    public LoginCLIPrompt(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    private String showEmailPrompt() {
        System.out.println("What is your email?");
        return readUserInput();
    }

    private String showPasswordPrompt() {
        System.out.println("What is your password?");
        return readUserInput();
    }

    public void start() {
        String loginEmailInput = showEmailPrompt();
        String loginPasswordInput = showPasswordPrompt();

        try {
            loginUseCase.login(loginEmailInput, loginPasswordInput);
        } catch (AuthorizationException | LoadingUsersException e) {
            System.err.println(e.getMessage());
        }
    }

}
