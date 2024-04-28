package de.dhbw.karlsruhe.students.mailflow.core.application.email.answer;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.HeaderKey;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FindPreviousEmailsService implements FindEmailUseCase {
  private final MailboxRepository mailboxRepository;
  private final AuthSessionUseCase authSession;

  public FindPreviousEmailsService(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
    this.authSession = authSession;
  }

  @Override
  public List<Email> find(Email email) throws MailboxSavingException, MailboxLoadingException {
    List<Email> foundEmails = new ArrayList<>();
    for (MailboxType type : MailboxType.values()) {
      Mailbox mailbox =
          mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);
      Set<Email> emails = mailbox.getEmailsWithHeaderKey(HeaderKey.IN_REPLY_TO.getKeyName());
      foundEmails.addAll(emails);
    }

    return foundEmails;
  }
}
