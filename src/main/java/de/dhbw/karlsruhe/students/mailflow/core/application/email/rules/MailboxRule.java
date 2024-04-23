package de.dhbw.karlsruhe.students.mailflow.core.application.email.rules;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

public interface MailboxRule {
  MailboxRuleResult runOnEmail(Email email) throws MailboxSavingException, MailboxLoadingException;
}
