package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * Provides functionality that is needed across all ProvideEmail-services
 *
 * @author Jonas-Karl
 */
abstract class AbstractProvideEmailsService implements ProvideEmailsUseCase {
  final MailboxRepository mailboxRepository;

  AbstractProvideEmailsService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  public int getEmailCount(Address sessionUser) {
    try {
      return provideEmails(sessionUser).size();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      return 0;
    }
  }
}
