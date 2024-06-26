package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

public interface MarkEmailUseCase {

  void mark(Email email) throws MailboxSavingException, MailboxLoadingException;

  /**
   * @return the verb that describes the action name that should fit in the following sentence:
   *         "Which email do you want to mark as %s?"
   */
  String getActionName();
}
