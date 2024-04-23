package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.result;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

/**
 * @author jens1o
 */
public class MarkAsSpam extends DeliverIntoFolder {

  public MarkAsSpam(MailboxRepository repository, String reason) {
    super(MailboxType.SPAM, repository);
    // TODO: Use central logger?
    System.out.printf("Marked as spam because of %s%n".formatted(reason));
  }
}
