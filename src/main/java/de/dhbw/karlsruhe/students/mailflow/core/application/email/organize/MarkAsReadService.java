package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public class MarkAsReadService implements MarkEmailUseCase {

  private final MailboxRepository mailboxRepository;

  public MarkAsReadService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void mark(Address mailboxOwner, Email email)
      throws MailboxSavingException, MailboxLoadingException {
    for (MailboxType type : MailboxType.values()) {
      Mailbox mailbox = mailboxRepository.findByAddressAndType(mailboxOwner, type);
      if (!mailbox.getEmailList().contains(email)) {
        continue;
      }
      mailbox.markWithLabel(email, Label.READ);
      mailboxRepository.save(mailbox);
    }
  }
}
