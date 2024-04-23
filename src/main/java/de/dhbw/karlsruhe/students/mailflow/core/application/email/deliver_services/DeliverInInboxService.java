package de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author jens1o
 */
public class DeliverInInboxService extends DeliverIntoFolderService {

  public DeliverInInboxService(MailboxRepository mailboxRepository) {
    super(MailboxType.INBOX, mailboxRepository);
  }
}
