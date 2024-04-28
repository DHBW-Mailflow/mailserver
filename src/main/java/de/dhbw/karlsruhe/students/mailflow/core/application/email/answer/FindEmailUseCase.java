package de.dhbw.karlsruhe.students.mailflow.core.application.email.answer;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.List;

public interface FindEmailUseCase {

  List<Email> find(Email email) throws MailboxSavingException, MailboxLoadingException;
}
