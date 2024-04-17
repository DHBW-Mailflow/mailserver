package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

/**
 * @author seiferla , Jonas-Karl
 */
abstract class GenericProvideEmailsService implements ProvideEmailsUseCase {
  private final MailboxRepository mailboxRepository;
  private final MailboxType mailboxType;
  private final Label[] labels;

  protected GenericProvideEmailsService(
      MailboxRepository mailboxRepository, MailboxType mailboxType, Label... labels) {
    this.mailboxRepository = mailboxRepository;
    this.mailboxType = mailboxType;
    this.labels = labels;
  }

  @Override
  public void markEmailAsRead(Email email, Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, mailboxType);
    mailbox.markWithLabel(email, Label.READ);
    mailboxRepository.save(mailbox);
  }

  @Override
  public List<Email> provideEmails(Address sessionUserAddress)
      throws MailboxSavingException, MailboxLoadingException {
    var mailbox = mailboxRepository.findByAddressAndType(sessionUserAddress, mailboxType);
    return mailbox.getEmailsWithLabel(labels);
  }

  @Override
  public String getMailboxName() {
    return mailboxType.getStoringName();
  }
}
