package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author seiferla
 */
public class ProvideSpamEmailsService extends GenericProvideEmailsService {
  public ProvideSpamEmailsService(MailboxRepository mailboxRepository) {
    super(mailboxRepository, MailboxType.SPAM, Label.UNREAD, Label.READ);
  }
}