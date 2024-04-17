package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public interface SearchEmailUseCase {

  List<Email> searchContentInEmails(String content, Address address, MailboxType type)throws MailboxSavingException, MailboxLoadingException;
}
