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
 * provides functionality to find emails in one specific mailboxType
 *
 * @author seiferla , Jonas-Karl
 */
abstract class AbstractProvideMailboxTypeEmailsService extends AbstractProvideEmailsService {
  private final MailboxType mailboxType;
  private final Label[] labels;

  protected AbstractProvideMailboxTypeEmailsService(
      AuthSessionUseCase authSession,
      MailboxRepository mailboxRepository,
      MailboxType mailboxType,
      Label... labels) {
    super(authSession, mailboxRepository);
    this.mailboxType = mailboxType;
    this.labels = labels;
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
