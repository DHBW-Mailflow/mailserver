package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author seiferla
 */
public class ProvideDeletedEmailsService extends GenericProvideEmailsService {
  public ProvideDeletedEmailsService(MailboxRepository mailboxRepository) {
    super(mailboxRepository, MailboxType.DELETED, Label.UNREAD, Label.READ);
  }
}
