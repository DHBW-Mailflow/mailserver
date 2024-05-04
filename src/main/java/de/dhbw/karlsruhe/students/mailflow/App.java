package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSession;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.UCCollectionAuth;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.MailboxRule;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.UCCollectionSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserCreator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalPasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalUserCreator;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.JSONMailboxConverter;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam.DetectSpamOnIncomingMailMailboxRule;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.ExportMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.FileUserRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.FileUserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.MainCLIPrompt;

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
    final MailboxRule spamDetector = new DetectSpamOnIncomingMailMailboxRule(mailboxRepository);
    final UserSettingsRepository userSettingsRepository = new FileUserSettingsRepository();
    final ExportMailboxRepository exportMailboxRepository = new ExportMailboxRepository();

    /// UseCases / Services
    final AuthSessionUseCase authSession = new AuthSession();

    final UCCollectionAuth collectionAuth =
        UCCollectionAuth.init(authSession, userRepository, passwordChecker, userCreator);
    final EmailSendUseCase sendEmails =
        new EmailSendService(authSession, mailboxRepository, spamDetector);
    final UCCollectionProvideEmails provideEmails =
        UCCollectionProvideEmails.init(authSession, mailboxRepository);
    final UCCollectionOrganizeEmails organizeEmails =
        UCCollectionOrganizeEmails.init(authSession, mailboxRepository);
    final UCCollectionSearchEmail searchEmails =
        UCCollectionSearchEmail.init(provideEmails.provideAllEmailsService());
    final UCCollectionSettings collectionSettings =
        UCCollectionSettings.init(
            authSession,
            userSettingsRepository,
            userRepository,
            mailboxRepository,
            exportMailboxRepository);
    final UCCollectionAnswerEmails answerEmails = UCCollectionAnswerEmails.init(sendEmails);

    /// Start
    Server server =
        new MainCLIPrompt(
            collectionAuth,
            sendEmails,
            provideEmails,
            organizeEmails,
            searchEmails,
            collectionSettings,
            answerEmails);
    server.start();
  }
}
