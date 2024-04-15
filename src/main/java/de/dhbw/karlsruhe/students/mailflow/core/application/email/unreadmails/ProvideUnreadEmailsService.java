package de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import java.util.List;

public class ProvideUnreadEmailsService implements ProvideUnreadEmailsUseCase {

  private final MailboxRepository mailboxRepository;

  public ProvideUnreadEmailsService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public List<Email> provideUnreadEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, MailboxType.INBOX);
    return mailbox.getEmailList();
  }
}