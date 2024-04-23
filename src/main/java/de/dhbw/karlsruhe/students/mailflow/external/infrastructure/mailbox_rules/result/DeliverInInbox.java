package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.result;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author jens1o
 */
public class DeliverInInbox extends DeliverIntoFolder {

  public DeliverInInbox(MailboxRepository mailboxRepository) {
    super(MailboxType.INBOX, mailboxRepository);
  }
}
