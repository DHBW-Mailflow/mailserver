package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public interface ProvideEmailsUseCase {

  List<Email> provideEmails(Address sessionUserAddress)
      throws MailboxSavingException, MailboxLoadingException;

  String getMailboxName();

  int getEmailCount(Address sessionUser);
}
