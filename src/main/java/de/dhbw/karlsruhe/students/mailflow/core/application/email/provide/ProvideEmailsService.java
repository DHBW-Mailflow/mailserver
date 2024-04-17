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

  public ProvideEmailsService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void markEmailAsRead(Email email, Address address, MailboxType mailboxType)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, mailboxType);
    mailbox.markWithLabel(email, Label.READ);
    mailboxRepository.save(mailbox);
  }

  @Override
  public List<Email> provideEmails(
      Address sessionUserAddress, MailboxType mailboxType, Label... labels)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(sessionUserAddress, mailboxType);
    return mailbox.getEmailsWithLabel(labels);
  }
}
