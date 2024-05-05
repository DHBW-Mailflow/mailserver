package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import java.util.Objects;

/**
 * As we got many useCases to provide emails, we need a collection to reduce the amount of
 * parameters
 *
 * @author Jonas-Karl
 */
public final class UCCollectionProvideEmails {
  private final ProvideDeletedEmailsService provideDeletedEmailsUseCase;
  private final ProvideSpamEmailsService provideSpamEmailsService;
  private final ProvideInboxReadEmailsService provideInboxReadEmailsUseCase;
  private final ProvideInboxUnreadEmailsService provideInboxUnreadEmailsUseCase;
  private final ProvideAllInboxEmailsService provideAllInboxEmailsService;
  private final ProvideSentEmailsService provideSentEmailsUseCase;
  private final ProvideAllReadEmailsService provideAllReadEmailsService;
  private final ProvideAllUnreadEmailsService provideAllUnreadEmailsService;
  private final ProvideAllEmailsService provideAllEmailsService;

  /** */
  public UCCollectionProvideEmails(
      ProvideDeletedEmailsService provideDeletedEmailsUseCase,
      ProvideSpamEmailsService provideSpamEmailsService,
      ProvideInboxReadEmailsService provideInboxReadEmailsUseCase,
      ProvideInboxUnreadEmailsService provideInboxUnreadEmailsUseCase,
      ProvideAllInboxEmailsService provideAllInboxEmailsService,
      ProvideSentEmailsService provideSentEmailsUseCase,
      ProvideAllReadEmailsService provideAllReadEmailsService,
      ProvideAllUnreadEmailsService provideAllUnreadEmailsService,
      ProvideAllEmailsService provideAllEmailsService) {
    this.provideDeletedEmailsUseCase = provideDeletedEmailsUseCase;
    this.provideSpamEmailsService = provideSpamEmailsService;
    this.provideInboxReadEmailsUseCase = provideInboxReadEmailsUseCase;
    this.provideInboxUnreadEmailsUseCase = provideInboxUnreadEmailsUseCase;
    this.provideAllInboxEmailsService = provideAllInboxEmailsService;
    this.provideSentEmailsUseCase = provideSentEmailsUseCase;
    this.provideAllReadEmailsService = provideAllReadEmailsService;
    this.provideAllUnreadEmailsService = provideAllUnreadEmailsService;
    this.provideAllEmailsService = provideAllEmailsService;
  }

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

  public ProvideDeletedEmailsService provideDeletedEmailsUseCase() {
    return provideDeletedEmailsUseCase;
  }

  public ProvideSpamEmailsService provideSpamEmailsService() {
    return provideSpamEmailsService;
  }

  public ProvideInboxReadEmailsService provideInboxReadEmailsUseCase() {
    return provideInboxReadEmailsUseCase;
  }

  public ProvideInboxUnreadEmailsService provideInboxUnreadEmailsUseCase() {
    return provideInboxUnreadEmailsUseCase;
  }

  public ProvideAllInboxEmailsService provideAllInboxEmailsService() {
    return provideAllInboxEmailsService;
  }

  public ProvideSentEmailsService provideSentEmailsUseCase() {
    return provideSentEmailsUseCase;
  }

  public ProvideAllReadEmailsService provideAllReadEmailsService() {
    return provideAllReadEmailsService;
  }

  public ProvideAllUnreadEmailsService provideAllUnreadEmailsService() {
    return provideAllUnreadEmailsService;
  }

  public ProvideAllEmailsService provideAllEmailsService() {
    return provideAllEmailsService;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (UCCollectionProvideEmails) obj;
    return Objects.equals(this.provideDeletedEmailsUseCase, that.provideDeletedEmailsUseCase)
        && Objects.equals(this.provideSpamEmailsService, that.provideSpamEmailsService)
        && Objects.equals(this.provideInboxReadEmailsUseCase, that.provideInboxReadEmailsUseCase)
        && Objects.equals(
            this.provideInboxUnreadEmailsUseCase, that.provideInboxUnreadEmailsUseCase)
        && Objects.equals(this.provideAllInboxEmailsService, that.provideAllInboxEmailsService)
        && Objects.equals(this.provideSentEmailsUseCase, that.provideSentEmailsUseCase)
        && Objects.equals(this.provideAllReadEmailsService, that.provideAllReadEmailsService)
        && Objects.equals(this.provideAllUnreadEmailsService, that.provideAllUnreadEmailsService)
        && Objects.equals(this.provideAllEmailsService, that.provideAllEmailsService);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        provideDeletedEmailsUseCase,
        provideSpamEmailsService,
        provideInboxReadEmailsUseCase,
        provideInboxUnreadEmailsUseCase,
        provideAllInboxEmailsService,
        provideSentEmailsUseCase,
        provideAllReadEmailsService,
        provideAllUnreadEmailsService,
        provideAllEmailsService);
  }

  @Override
  public String toString() {
    return "UCCollectionProvideEmails["
        + "provideDeletedEmailsUseCase="
        + provideDeletedEmailsUseCase
        + ", "
        + "provideSpamEmailsService="
        + provideSpamEmailsService
        + ", "
        + "provideInboxReadEmailsUseCase="
        + provideInboxReadEmailsUseCase
        + ", "
        + "provideInboxUnreadEmailsUseCase="
        + provideInboxUnreadEmailsUseCase
        + ", "
        + "provideAllInboxEmailsService="
        + provideAllInboxEmailsService
        + ", "
        + "provideSentEmailsUseCase="
        + provideSentEmailsUseCase
        + ", "
        + "provideAllReadEmailsService="
        + provideAllReadEmailsService
        + ", "
        + "provideAllUnreadEmailsService="
        + provideAllUnreadEmailsService
        + ", "
        + "provideAllEmailsService="
        + provideAllEmailsService
        + ']';
  }
}
