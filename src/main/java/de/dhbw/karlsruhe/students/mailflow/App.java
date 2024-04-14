package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginService;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegistrationService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.SendEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.SendEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.FileUserRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalPasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalUserCreator;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.CLIPrompt;

/**
 * Hello world!
 *
 * @author jens1o, Jonas-Karl, seiferla
 */
public class App {
    public static void main(String[] args) {

        LoginUseCase loginUseCase = new LoginService(new FileUserRepository(), new LocalPasswordChecker());
        RegisterUseCase registerUseCase = new RegistrationService(new FileUserRepository(), new LocalUserCreator());
        SendEmailUseCase sendEmailUseCase = new SendEmailService(); //add repositories
        Server server = new CLIPrompt(loginUseCase, registerUseCase, sendEmailUseCase);
        server.start();
    }
}
