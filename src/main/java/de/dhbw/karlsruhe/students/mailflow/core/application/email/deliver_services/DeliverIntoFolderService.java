package de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services;

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
public class DeliverIntoFolderService implements DeliverService {

  private final MailboxType folder;
  private final MailboxRepository mailboxRepository;

  public DeliverIntoFolderService(MailboxType folder, MailboxRepository mailboxRepository) {
    this.folder = folder;
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void deliverEmailTo(Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(recipient, folder);
    boolean isRead = false;
    mailbox.deliverEmail(email, !isRead);
    mailboxRepository.save(mailbox);
  }
}
