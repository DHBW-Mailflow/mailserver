package de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * @author jens1o
 */
public class DeliverInInboxService implements DeliverUseCase {
  private final MailboxType folder;
  private final MailboxRepository mailboxRepository;
  private final Label[] labels;

  public DeliverInInboxService(MailboxRepository mailboxRepository) {
    this.folder = MailboxType.INBOX;
    this.mailboxRepository = mailboxRepository;
    this.labels = new Label[] {Label.UNREAD};
  }

  @Override
  public void deliverEmailTo(Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(recipient, folder);
    mailbox.markWithLabel(email, labels);
    mailboxRepository.save(mailbox);
  }
}
