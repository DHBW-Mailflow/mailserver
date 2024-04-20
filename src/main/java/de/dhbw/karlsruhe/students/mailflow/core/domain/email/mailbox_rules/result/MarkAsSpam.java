package de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.result;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * @author jens1o
 */
public class MarkAsSpam implements MailboxRuleResult {

  private String humanReadableReason;

  @Override
  public void execute(MailboxRepository mailbox, Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    // TODO: Use central logger?
    System.out.println(
        email.getSubject().subject() + " was marked as spam. Reason: " + this.humanReadableReason);

    new DeliverIntoFolder().setFolder(MailboxType.SPAM).execute(mailbox, recipient, email);
  }

  public MailboxRuleResult becauseOf(String humanReadableReason) {
    this.humanReadableReason = humanReadableReason;

    return this;
  }

}
