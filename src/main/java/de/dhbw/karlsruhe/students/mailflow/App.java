package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthService;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegistrationService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideDeletedEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideInboxReadEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideInboxUnreadEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideSentEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideSpamEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
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
    final UCCollectionProvideEmails provideEmails = initProvideEmailUCs(mailboxRepository);

    /// Start
    Server server = new MainCLIPrompt(authUseCase, registerUseCase, sendEmails, provideEmails);
    server.start();
  }

  /**
   * @param mailboxRepository The mailbox repository to use
   * @return A collection of all use cases to provide emails
   */
  private static UCCollectionProvideEmails initProvideEmailUCs(
      FileMailboxRepository mailboxRepository) {
    final ProvideSpamEmailsService provideSpam = new ProvideSpamEmailsService(mailboxRepository);
    final ProvideInboxUnreadEmailsService provideInboxUnread =
        new ProvideInboxUnreadEmailsService(mailboxRepository);
    final ProvideInboxReadEmailsService provideInboxRead =
        new ProvideInboxReadEmailsService(mailboxRepository);
    final ProvideSentEmailsService provideSent = new ProvideSentEmailsService(mailboxRepository);
    final ProvideDeletedEmailsService provideDeleted =
        new ProvideDeletedEmailsService(mailboxRepository);

    return new UCCollectionProvideEmails(
        provideDeleted, provideSpam, provideInboxRead, provideInboxUnread, provideSent);
  }
}
