package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

/**
 * @author Jonas-Karl
 */
public class MarkAsUnreadService implements MarkEmailUseCase {

  private final AuthSessionUseCase authSession;
  private final MailboxRepository mailboxRepository;

  public MarkAsUnreadService(AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void mark(Email email) throws MailboxSavingException, MailboxLoadingException {
    for (MailboxType type : MailboxType.values()) {
      Mailbox mailbox =
          mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);
      if (!mailbox.getEmailList().contains(email)) {
        continue;
      }
      mailbox.markWithLabel(email, Label.UNREAD);
      mailboxRepository.save(mailbox);
    }
  }

  @Override
  public String getActionName() {
    return "unread";
  }
}
