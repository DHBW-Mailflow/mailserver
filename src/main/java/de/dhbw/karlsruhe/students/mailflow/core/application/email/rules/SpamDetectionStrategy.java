package de.dhbw.karlsruhe.students.mailflow.core.application.email.rules;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

public interface SpamDetectionStrategy {
  boolean isSpam(Email email) throws MailboxSavingException, MailboxLoadingException;

  String getReason();
}
