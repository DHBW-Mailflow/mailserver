package de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.result;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * @author jens1o
 */
public class DeliverIntoFolder implements MailboxRuleResult {

  private MailboxType folder;
  private boolean isRead = false;

  public DeliverIntoFolder setFolder(MailboxType folder) {
    this.folder = folder;

    return this;
  }

  public DeliverIntoFolder setIsRead(boolean isUnread) {
    this.isRead = isUnread;

    return this;
  }

  @Override
  public void execute(MailboxRepository mailboxRepository, Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(recipient, folder);
    mailbox.deliverEmail(email, !this.isRead);

    mailboxRepository.save(mailbox);
  }

}
