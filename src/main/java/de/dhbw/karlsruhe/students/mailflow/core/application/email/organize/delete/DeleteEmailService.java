package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.delete;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

public class DeleteEmailService implements DeleteEmailsUseCase {

  private final AuthSessionUseCase authSession;
  private final MailboxRepository mailboxRepository;

  public DeleteEmailService(AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public void delete(Email email) throws MailboxSavingException, MailboxLoadingException {

    Mailbox mailbox = providePossibleEmailsToDelete();
    if (mailbox == null) {
      throw new MailboxLoadingException("No mailbox found to delete email");
    }
    mailbox.deleteEmail(email);
    mailboxRepository.save(mailbox);
  }

  @Override
  public Mailbox providePossibleEmailsToDelete()
      throws MailboxSavingException, MailboxLoadingException {

    for (MailboxType type : MailboxType.values()) {
      if (type.equals(MailboxType.DELETED)) {
        continue;
      }
      return mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);
    }
    return null;
  }
}
