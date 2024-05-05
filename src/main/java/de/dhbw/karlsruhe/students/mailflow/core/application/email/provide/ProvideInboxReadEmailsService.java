package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.List;

/**
 * @author seiferla
 */
public class ProvideInboxReadEmailsService implements ProvideEmailsUseCase {
  private final MailboxType mailboxType;
  private final Label[] labels;
  private final MailboxRepository mailboxRepository;
  private final AuthSessionUseCase authSession;

  public ProvideInboxReadEmailsService(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
    this.mailboxType = MailboxType.INBOX;
    this.labels = new Label[] {Label.READ};
  }

  public int getEmailCount() {
    try {
      return provideEmails().size();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      return 0;
    }
  }

  @Override
  public List<Email> provideEmails() throws MailboxSavingException, MailboxLoadingException {
    var mailbox =
        mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), mailboxType);
    return mailbox.getEmailsWithLabel(labels);
  }

  @Override
  public String getMailboxName() {
    return mailboxType.getStoringName();
  }
}
