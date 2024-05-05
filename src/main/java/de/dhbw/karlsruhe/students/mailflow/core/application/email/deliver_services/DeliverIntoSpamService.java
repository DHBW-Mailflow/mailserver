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
public class DeliverIntoSpamService implements DeliverUseCase {

  private final MailboxType folder;
  private final MailboxRepository mailboxRepository;
  private final Label[] labels;

  public DeliverIntoSpamService(MailboxRepository repository, String reason) {
    this.folder = MailboxType.SPAM;
    this.mailboxRepository = repository;
    this.labels = new Label[] {Label.UNREAD};
    // TODO: Use central logger?
    System.out.printf("Marked as spam because of %s%n".formatted(reason));
  }

  @Override
  public void deliverEmailTo(Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(recipient, folder);
    mailbox.markWithLabel(email, labels);
    mailboxRepository.save(mailbox);
  }
}
