package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthService;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegistrationService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.FileUserRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalPasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalUserCreator;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.MainCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.JSONMailboxConverter;

/**
 * Hello world!
 *
 * @author jens1o, Jonas-Karl, seiferla
 */
public class App {
  public static void main(String[] args) {
    AuthUseCase authUseCase = new AuthService(new FileUserRepository(), new LocalPasswordChecker());
    RegisterUseCase registerUseCase =
        new RegistrationService(new FileUserRepository(), new LocalUserCreator());
    EmailSendUseCase emailSendUseCase =
        new EmailSendService(new FileMailboxRepository(new JSONMailboxConverter()));
    ProvideEmailsUseCase provideEmailsUseCase =
        new ProvideEmailsService(new FileMailboxRepository(new JSONMailboxConverter()));
    Server server =
        new MainCLIPrompt(
            authUseCase, registerUseCase, emailSendUseCase, provideEmailsUseCase);
    server.start();
  }
}
