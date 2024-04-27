package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.delete;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.Optional;
import java.util.Set;

public class DeleteEmailService implements DeleteEmailsUseCase {

  private final AuthSessionUseCase authSession;
  private final MailboxRepository mailboxRepository;

  public DeleteEmailService(AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void delete(Email email) throws MailboxSavingException, MailboxLoadingException {

    boolean didFindEmail = false;

    for (MailboxType type : MailboxType.values()) {
      Mailbox mailbox =
          mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);

      Optional<Set<Label>> deletionResult = mailbox.deleteEmail(email);

      if (deletionResult.isEmpty()) {
        // e-mail was not in this mailbox, try next
        continue;
      }

      // found and deleted the mail from the original mailbox,
      // now move it to the deleted mailbox
      moveToDeleted(email, deletionResult.get());

      // we successfully found and marked the e-mail as deleted upon reaching here,
      // so there is nothing left to do

      didFindEmail = true;
      mailboxRepository.save(mailbox);
      break;
    }

    if (!didFindEmail) {
      throw new IllegalStateException("email not found in any mailbox");
    }
  }

  private void moveToDeleted(Email email, Set<Label> labels)
      throws MailboxSavingException, MailboxLoadingException {

    Mailbox deletedMailbox =
        mailboxRepository.findByAddressAndType(
            authSession.getSessionUserAddress(), MailboxType.DELETED);

    deletedMailbox.markWithLabel(email, labels.toArray(new Label[] {}));
    mailboxRepository.save(deletedMailbox);
  }
}
