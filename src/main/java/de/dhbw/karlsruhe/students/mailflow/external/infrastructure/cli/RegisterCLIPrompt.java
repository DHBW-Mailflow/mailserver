package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public class RegisterCLIPrompt extends AbstractCLIPrompt {
    private final RegisterUseCase registerUseCase;

    public RegisterCLIPrompt(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @Override
    public void start() {
        System.out.println("What's your new email?");
        String email = readUserInput();
        System.out.println("What's your new password?");
        String password = readUserInput();
        try {
            registerUseCase.register(Address.from(email), password);
        } catch (AuthorizationException | LoadingUsersException e) {
            System.err.println(e.getMessage());
        }
    }
}
