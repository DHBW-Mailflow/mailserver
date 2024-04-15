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

public class ProvideEmailsService implements ProvideEmailsUseCase {
  private final MailboxRepository mailboxRepository;

  public ProvideEmailsService(
      MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public List<Email> provideUnreadEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, MailboxType.INBOX);
    return mailbox.getEmailsWithLabel(Label.UNREAD);
  }

  @Override
  public void markEmailAsRead(Email email, Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, MailboxType.INBOX);
    mailbox.markWithLabel(email, Label.READ);
    mailboxRepository.save(mailbox);
  }

  @Override
  public List<Email> provideSpamEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, MailboxType.SPAM);
    return mailbox.getEmailsWithLabel(Label.UNREAD, Label.READ);
  }

  @Override
  public List<Email> provideDeletedEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, MailboxType.DELETED);
    return mailbox.getEmailsWithLabel(Label.UNREAD, Label.READ);
  }

  @Override
  public List<Email> provideReadEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, MailboxType.INBOX);
    return mailbox.getEmailsWithLabel(Label.READ);
  }
}
