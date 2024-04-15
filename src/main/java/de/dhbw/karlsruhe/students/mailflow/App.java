package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthService;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegistrationService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.readmails.ProvideReadEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.readmails.ProvideReadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
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

    Server server = new MainCLIPrompt(authUseCase, registerUseCase, emailSendUseCase);
    ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase =
        new ProvideUnreadEmailsService(new FileMailboxRepository(new JSONMailboxConverter()));
    ProvideReadEmailsUseCase provideReadEmailsUseCase =
        new ProvideReadEmailsService(new FileMailboxRepository(new JSONMailboxConverter()));
    Server server =
        new MainCLIPrompt(
            authUseCase, registerUseCase, provideUnreadEmailsUseCase, provideReadEmailsUseCase);
    server.start();
  }
}
