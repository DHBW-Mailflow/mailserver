package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public interface SearchSubjectEmailUseCase {


  List<Email> searchSubjectInEmails(String subject, Address address)
      throws MailboxSavingException, MailboxLoadingException;
}
