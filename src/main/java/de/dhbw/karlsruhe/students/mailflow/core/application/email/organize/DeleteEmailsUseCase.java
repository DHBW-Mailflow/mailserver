package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.io.FileNotFoundException;

public interface DeleteEmailsUseCase {
  void delete(Email email) throws MailboxSavingException, MailboxLoadingException, FileNotFoundException;

}
