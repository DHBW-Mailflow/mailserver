package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSession;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.UCCollectionAuth;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserCreator;
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
    final UserCreator userCreator = new LocalUserCreator();
    final PasswordChecker passwordChecker = new LocalPasswordChecker();

    /// UseCases / Services
    final AuthSessionUseCase authSession = new AuthSession();

    final UCCollectionAuth collectionAuth =
        UCCollectionAuth.init(authSession, userRepository, passwordChecker, userCreator);

    final EmailSendUseCase sendEmails = new EmailSendService(authSession, mailboxRepository);
    final UCCollectionProvideEmails provideEmails =
        UCCollectionProvideEmails.init(authSession, mailboxRepository);
    final UCCollectionOrganizeEmails organizeEmails =
        UCCollectionOrganizeEmails.init(authSession, mailboxRepository);
    final UCCollectionSearchEmail searchEmails =
        UCCollectionSearchEmail.init(provideEmails.provideAllEmailsService());

    /// Start
    Server server =
        new MainCLIPrompt(collectionAuth, sendEmails, provideEmails, organizeEmails, searchEmails);
    server.start();
  }
}
