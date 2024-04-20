package de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.result;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public interface MailboxRuleResult {
  /**
   * applies the matching rule that should be applied according to the MailboxRule
   *
   * @param mailbox
   * @param recipient
   * @param email
   * @throws MailboxLoadingException
   * @throws MailboxSavingException
   */
  public void execute(MailboxRepository mailbox, Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException;
}
