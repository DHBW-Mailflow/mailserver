package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthService;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegistrationService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
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
    /// Repositories
    final FileMailboxRepository mailboxRepository =
        new FileMailboxRepository(new JSONMailboxConverter());
    final UserRepository userRepository = new FileUserRepository();

    /// UseCases / Services
    final AuthUseCase authUseCase = new AuthService(userRepository, new LocalPasswordChecker());
    final RegisterUseCase registerUseCase =
        new RegistrationService(userRepository, new LocalUserCreator());
    final EmailSendUseCase sendEmails = new EmailSendService(mailboxRepository);
    final UCCollectionProvideEmails provideEmails =
        UCCollectionProvideEmails.init(mailboxRepository);
    final UCCollectionOrganizeEmails organizeEmails =
        UCCollectionOrganizeEmails.init(mailboxRepository);
    final UCCollectionSearchEmail searchEmails = UCCollectionSearchEmail.init(provideEmails.provideAllEmailsService());

    /// Start
    Server server =
        new MainCLIPrompt(authUseCase, registerUseCase, sendEmails, provideEmails, organizeEmails, searchEmails);
    server.start();
  }
}
