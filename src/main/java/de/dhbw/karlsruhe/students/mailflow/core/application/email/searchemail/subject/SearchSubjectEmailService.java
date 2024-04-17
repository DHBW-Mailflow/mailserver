package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.ArrayList;
import java.util.List;

public class SearchSubjectEmailService implements SearchSubjectEmailUseCase {

  private final MailboxRepository mailboxRepository;

  public SearchSubjectEmailService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public List<Email> searchSubjectInEmails(String subject, Address address)
      throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox;
    List<Email> emailList = new ArrayList<>();
    for (MailboxType type : MailboxType.values()) {
      mailbox = mailboxRepository.findByAddressAndType(address, type);
      emailList.addAll(mailbox.getEmailList());
    }
    return emailList.stream()
        .filter(email -> email.getSubject().subject().contains(subject))
        .toList();
  }
}
