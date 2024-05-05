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
public class DeliverInSentService implements DeliverUseCase {
  private final MailboxType folder;
  private final MailboxRepository mailboxRepository;
  private final Label[] labels;

  public DeliverInSentService(MailboxRepository mailboxRepository) {
    this.folder = MailboxType.SENT;
    this.mailboxRepository = mailboxRepository;
    this.labels = new Label[] {Label.READ};
  }

  @Override
  public void deliverEmailTo(Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(recipient, folder);
    mailbox.markWithLabel(email, labels);
    mailboxRepository.save(mailbox);
  }
}
