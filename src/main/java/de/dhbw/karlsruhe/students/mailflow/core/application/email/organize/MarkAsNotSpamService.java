package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import java.util.Optional;
import java.util.Set;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

/**
 * @author jens1o
 */
public class MarkAsNotSpamService implements MarkEmailUseCase {

  private AuthSessionUseCase authSession;
  private MailboxRepository mailboxRepository;

  public MarkAsNotSpamService(AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void mark(Email email) throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = this.mailboxRepository
        .findByAddressAndType(this.authSession.getSessionUserAddress(), MailboxType.SPAM);

    Optional<Set<Label>> optionalLabels = mailbox.deleteEmail(email);

    if (optionalLabels.isEmpty()) {
      throw new IllegalStateException("e-mail is not marked as spam!");
    }
    Set<Label> labels = optionalLabels.get();

    mailboxRepository.save(mailbox);

    mailbox = this.mailboxRepository.findByAddressAndType(this.authSession.getSessionUserAddress(),
        MailboxType.INBOX);
    mailbox.markWithLabel(email, labels.toArray(new Label[] {}));
    mailboxRepository.save(mailbox);
  }

  @Override
  public String getActionName() {
    return "not spam";
  }

}
