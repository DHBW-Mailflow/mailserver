package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import java.util.List;

public class SearchEmailService implements SearchEmailUseCase {

  private final MailboxRepository mailboxRepository;

  public SearchEmailService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }


  public List<Email> searchContentInEmails(String content) {
    return mailboxRepository.findAllEmails().stream()
        .filter(email -> email.getContent().contains(content))
        .toList();
  }
}
