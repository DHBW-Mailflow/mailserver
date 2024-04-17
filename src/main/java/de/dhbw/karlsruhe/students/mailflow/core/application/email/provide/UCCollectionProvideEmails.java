package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

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
    ProvideSentEmailsService provideSentEmailsUseCase) {}
