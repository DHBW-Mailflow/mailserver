package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.List;

public interface SearchEmailUseCase {

  List<Email> searchEmails(String content) throws MailboxSavingException, MailboxLoadingException;
}
