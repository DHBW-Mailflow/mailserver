package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;

/**
 * As we got many useCases to provide emails, we need a collection to reduce the amount of
 * parameters
 *
 * @author Jonas-Karl
 */
public record UCCollectionProvideEmails(
    ProvideDeletedEmailsService provideDeletedEmailsUseCase,
    ProvideSpamEmailsService provideSpamEmailsService,
    ProvideInboxReadEmailsService provideInboxReadEmailsUseCase,
    ProvideInboxUnreadEmailsService provideInboxUnreadEmailsUseCase,
    ProvideAllInboxEmailsService provideAllInboxEmailsService,
    ProvideSentEmailsService provideSentEmailsUseCase,
    ProvideAllReadEmailsService provideAllReadEmailsService,
    ProvideAllUnreadEmailsService provideAllUnreadEmailsService,
    ProvideAllEmailsService provideAllEmailsService) {

  public static UCCollectionProvideEmails init(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    return new UCCollectionProvideEmails(
        new ProvideDeletedEmailsService(authSession, mailboxRepository),
        new ProvideSpamEmailsService(authSession, mailboxRepository),
        new ProvideInboxReadEmailsService(authSession, mailboxRepository),
        new ProvideInboxUnreadEmailsService(authSession, mailboxRepository),
        new ProvideAllInboxEmailsService(authSession, mailboxRepository),
        new ProvideSentEmailsService(authSession, mailboxRepository),
        new ProvideAllReadEmailsService(authSession, mailboxRepository),
        new ProvideAllUnreadEmailsService(authSession, mailboxRepository),
        new ProvideAllEmailsService(authSession, mailboxRepository));
  }
}
