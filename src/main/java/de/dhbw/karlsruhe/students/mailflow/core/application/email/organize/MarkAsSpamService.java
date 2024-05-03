package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;
import java.util.Optional;
import java.util.Set;

/**
 * @author jens1o
 */
public class MarkAsSpamService implements MarkEmailUseCase {

  private final AuthSessionUseCase authSession;
  private final FileMailboxRepository mailboxRepository;

  public MarkAsSpamService(
      AuthSessionUseCase authSession, FileMailboxRepository mailboxRepository) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void mark(Email email) throws MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(),
        MailboxType.INBOX);

    Optional<Set<Label>> deletionResult = mailbox.deleteEmail(email);

    if (deletionResult.isEmpty()) {
      throw new IllegalStateException("e-mail to mark as spam could not be found in inbox");
    }

    // found and deleted the mail from the original mailbox,
    // now move it to the spam mailbox
    moveToSpam(email, deletionResult.get());

    // we successfully found and marked the e-mail as spam upon reaching here,
    // so there is nothing left to do except to persist our changes
    mailboxRepository.save(mailbox);
  }

  private void moveToSpam(Email email, Set<Label> labels)
      throws MailboxLoadingException, MailboxSavingException {
    Mailbox spamMailbox =
        mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(),
            MailboxType.SPAM);

    spamMailbox.markWithLabel(email, labels.toArray(new Label[] {}));
    mailboxRepository.save(spamMailbox);
  }

  @Override
  public String getActionName() {
    return "spam";
  }

}
