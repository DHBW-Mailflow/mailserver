package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;

public record UCCollectionOrganizeEmails(
    MarkAsReadService markAsReadService, MarkAsUnreadService markAsUnreadService) {
  public static UCCollectionOrganizeEmails init(MailboxRepository mailboxRepository) {
    return new UCCollectionOrganizeEmails(
        new MarkAsReadService(mailboxRepository), new MarkAsUnreadService(mailboxRepository));
  }
}
