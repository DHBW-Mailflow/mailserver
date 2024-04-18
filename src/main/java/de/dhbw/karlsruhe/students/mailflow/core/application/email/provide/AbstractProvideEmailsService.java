package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

/**
 * Provides functionality that is needed across all ProvideEmail-services
 *
 * @author Jonas-Karl
 */
abstract class AbstractProvideEmailsService implements ProvideEmailsUseCase {
  final MailboxRepository mailboxRepository;
  final AuthSessionUseCase authSession;

  AbstractProvideEmailsService(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    super();
    this.mailboxRepository = mailboxRepository;
    this.authSession = authSession;
  }

  public int getEmailCount() {
    try {
      return provideEmails().size();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      return 0;
    }
  }
}
