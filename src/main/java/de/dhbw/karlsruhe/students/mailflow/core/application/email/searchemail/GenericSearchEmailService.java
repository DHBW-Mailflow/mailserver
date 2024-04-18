package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericSearchEmailService implements SearchEmailUseCase {

  private final MailboxRepository mailboxRepository;

  protected GenericSearchEmailService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public List<Email> searchEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox;
    List<Email> emailList = new ArrayList<>();
    for (MailboxType type : MailboxType.values()) {
      mailbox = mailboxRepository.findByAddressAndType(address, type);
      emailList.addAll(mailbox.getEmailList());
    }
    return emailList;
  }
}

