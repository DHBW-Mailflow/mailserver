package de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.result.MailboxRuleResult;

public interface MailboxRule {
  public MailboxRuleResult runOnEmail(MailboxRepository repository, Email email);
}
