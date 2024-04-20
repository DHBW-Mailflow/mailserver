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
public class DeliverInInbox implements MailboxRuleResult {

  @Override
  public void execute(MailboxRepository mailbox, Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    new DeliverIntoFolder().setFolder(MailboxType.INBOX).execute(mailbox, recipient, email);
  }
}
