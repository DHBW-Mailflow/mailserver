package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;

/**
 * As we got many useCases to provide emails, we need a collection to reduce the amount of
 * parameters
 *
 * @author Jonas-Karl
 */
public record UCCollectionProvideEmails(
    ProvideDeletedEmailsService provideDeletedEmailsUseCase,
    ProvideSpamEmailsService provideSpamEmailsUseCase,
    ProvideInboxReadEmailsService provideInboxReadEmailsUseCase,
    ProvideInboxUnreadEmailsService provideInboxUnreadEmailsUseCase,
    ProvideSentEmailsService provideSentEmailsUseCase,
    ProvideAllReadEmailsService provideAllReadEmailsService,
    ProvideAllUnreadEmailsService provideAllUnreadEmailsService) {

  public static UCCollectionProvideEmails init(FileMailboxRepository mailboxRepository) {
    return new UCCollectionProvideEmails(
        new ProvideDeletedEmailsService(mailboxRepository),
        new ProvideSpamEmailsService(mailboxRepository),
        new ProvideInboxReadEmailsService(mailboxRepository),
        new ProvideInboxUnreadEmailsService(mailboxRepository),
        new ProvideSentEmailsService(mailboxRepository),
        new ProvideAllReadEmailsService(mailboxRepository),
        new ProvideAllUnreadEmailsService(mailboxRepository));
  }
}
