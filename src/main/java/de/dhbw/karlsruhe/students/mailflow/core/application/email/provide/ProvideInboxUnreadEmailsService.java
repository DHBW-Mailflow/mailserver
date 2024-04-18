package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author seiferla
 */
public class ProvideInboxUnreadEmailsService extends AbstractProvideMailboxTypeEmailsService {
  public ProvideInboxUnreadEmailsService(MailboxRepository mailboxRepository) {
    super(mailboxRepository, MailboxType.INBOX, Label.UNREAD);
  }
}
