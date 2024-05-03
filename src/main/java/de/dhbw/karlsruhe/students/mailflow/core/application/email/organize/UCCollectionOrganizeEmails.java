package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark.MarkAsReadService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark.MarkAsUnreadService;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;

/**
 * @author Jonas-Karl
 */
public record UCCollectionOrganizeEmails(
    MarkAsReadService markAsReadService,
    MarkAsUnreadService markAsUnreadService,
    MarkAsSpamService markAsSpamService,
    MarkAsNotSpamService markAsNotSpamService,
    DeleteEmailService deleteEmailService) {
  public static UCCollectionOrganizeEmails init(
      AuthSessionUseCase authSession, FileMailboxRepository mailboxRepository) {
    return new UCCollectionOrganizeEmails(
        new MarkAsReadService(authSession, mailboxRepository),
        new MarkAsUnreadService(authSession, mailboxRepository),
        new MarkAsSpamService(authSession, mailboxRepository),
        new MarkAsNotSpamService(authSession, mailboxRepository),
        new DeleteEmailService(authSession, mailboxRepository));
  }
}
