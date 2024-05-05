package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark.MarkAsReadService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark.MarkAsUnreadService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import java.util.Objects;

/**
 * @author Jonas-Karl
 */
public final class UCCollectionOrganizeEmails {
  private final MarkAsReadService markAsReadService;
  private final MarkAsUnreadService markAsUnreadService;
  private final MarkAsSpamService markAsSpamService;
  private final MarkAsNotSpamService markAsNotSpamService;
  private final DeleteEmailService deleteEmailService;

  /** */
  public UCCollectionOrganizeEmails(
      MarkAsReadService markAsReadService,
      MarkAsUnreadService markAsUnreadService,
      MarkAsSpamService markAsSpamService,
      MarkAsNotSpamService markAsNotSpamService,
      DeleteEmailService deleteEmailService) {
    this.markAsReadService = markAsReadService;
    this.markAsUnreadService = markAsUnreadService;
    this.markAsSpamService = markAsSpamService;
    this.markAsNotSpamService = markAsNotSpamService;
    this.deleteEmailService = deleteEmailService;
  }

  public static UCCollectionOrganizeEmails init(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    return new UCCollectionOrganizeEmails(
        new MarkAsReadService(authSession, mailboxRepository),
        new MarkAsUnreadService(authSession, mailboxRepository),
        new MarkAsSpamService(authSession, mailboxRepository),
        new MarkAsNotSpamService(authSession, mailboxRepository),
        new DeleteEmailService(authSession, mailboxRepository));
  }

  public MarkAsReadService markAsReadService() {
    return markAsReadService;
  }

  public MarkAsUnreadService markAsUnreadService() {
    return markAsUnreadService;
  }

  public MarkAsSpamService markAsSpamService() {
    return markAsSpamService;
  }

  public MarkAsNotSpamService markAsNotSpamService() {
    return markAsNotSpamService;
  }

  public DeleteEmailService deleteEmailService() {
    return deleteEmailService;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (UCCollectionOrganizeEmails) obj;
    return Objects.equals(this.markAsReadService, that.markAsReadService)
        && Objects.equals(this.markAsUnreadService, that.markAsUnreadService)
        && Objects.equals(this.markAsSpamService, that.markAsSpamService)
        && Objects.equals(this.markAsNotSpamService, that.markAsNotSpamService)
        && Objects.equals(this.deleteEmailService, that.deleteEmailService);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        markAsReadService,
        markAsUnreadService,
        markAsSpamService,
        markAsNotSpamService,
        deleteEmailService);
  }

  @Override
  public String toString() {
    return "UCCollectionOrganizeEmails["
        + "markAsReadService="
        + markAsReadService
        + ", "
        + "markAsUnreadService="
        + markAsUnreadService
        + ", "
        + "markAsSpamService="
        + markAsSpamService
        + ", "
        + "markAsNotSpamService="
        + markAsNotSpamService
        + ", "
        + "deleteEmailService="
        + deleteEmailService
        + ']';
  }
}
