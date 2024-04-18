package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author seiferla
 */
public class ProvideSpamEmailsService extends AbstractProvideMailboxTypeEmailsService {
  public ProvideSpamEmailsService(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    super(authSession, mailboxRepository, MailboxType.SPAM, Label.UNREAD, Label.READ);
  }
}
