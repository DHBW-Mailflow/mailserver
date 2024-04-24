package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.delete;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.List;

public interface DeleteEmailsUseCase {
  void delete(Email email) throws MailboxSavingException, MailboxLoadingException;

  List<Email> providePossibleEmailsToDelete() throws MailboxSavingException, MailboxLoadingException;
}
