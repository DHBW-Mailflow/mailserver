package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.List;

public interface ProvideEmailsUseCase {

  List<Email> provideEmails() throws MailboxSavingException, MailboxLoadingException;

  String getMailboxName();

  int getEmailCount();
}
