package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;

/**
 * @author Jonas-Karl
 */
public record UCCollectionOrganizeEmails(
    MarkAsReadService markAsReadService, MarkAsUnreadService markAsUnreadService) {
  public static UCCollectionOrganizeEmails init(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    return new UCCollectionOrganizeEmails(
        new MarkAsReadService(authSession, mailboxRepository),
        new MarkAsUnreadService(authSession, mailboxRepository));
  }
}
