package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public class SearchEmailService implements SearchEmailUseCase {

  private final MailboxRepository mailboxRepository;

  public SearchEmailService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  public List<Email> searchContentInEmails(String content, Address address, MailboxType type)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, type);
    return mailbox.getEmailList().stream()
        .filter(email -> email.getContent().contains(content))
        .toList();
  }
}
