package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.ArrayList;
import java.util.List;

public class SearchContentEmailService extends GenericSearchEmailService {

  private final MailboxRepository mailboxRepository;

  public SearchContentEmailService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  public List<Email> searchContentInEmails(String content, Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox;
    List<Email> emailList = new ArrayList<>();
    for (MailboxType type : MailboxType.values()) {
      mailbox = mailboxRepository.findByAddressAndType(address, type);
      emailList.addAll(mailbox.getEmailList());
    }

    return emailList.stream()
        .filter(email -> email.getContent().contains(content))
        .toList();
  }
}
