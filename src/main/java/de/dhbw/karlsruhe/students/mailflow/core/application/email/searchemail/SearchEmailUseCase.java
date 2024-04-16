package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import java.util.List;

public interface SearchEmailUseCase {

  List<Email> searchContentInEmails(String content);
}
