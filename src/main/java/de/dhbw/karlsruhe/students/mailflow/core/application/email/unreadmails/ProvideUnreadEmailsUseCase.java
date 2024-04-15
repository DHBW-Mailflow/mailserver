package de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public interface ProvideUnreadEmailsUseCase {

  List<Email> provideUnreadEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException;
}
