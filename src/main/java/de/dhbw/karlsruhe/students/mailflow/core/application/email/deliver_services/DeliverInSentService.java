package de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author jens1o
 */
public class DeliverInSentService extends DeliverIntoFolderService {
  public DeliverInSentService(MailboxRepository mailboxRepository) {
    super(MailboxType.SENT, mailboxRepository, true);
  }
}
