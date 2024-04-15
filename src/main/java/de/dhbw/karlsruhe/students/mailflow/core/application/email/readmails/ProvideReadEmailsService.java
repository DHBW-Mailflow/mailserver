package de.dhbw.karlsruhe.students.mailflow.core.application.email.readmails;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public class ProvideReadEmailsService implements ProvideReadEmailsUseCase {

  private final MailboxRepository mailboxRepository;

  public ProvideReadEmailsService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public List<Email> provideReadEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, MailboxType.INBOX);
    return mailbox.getEmailsWithLabel(Label.READ);
  }
}
