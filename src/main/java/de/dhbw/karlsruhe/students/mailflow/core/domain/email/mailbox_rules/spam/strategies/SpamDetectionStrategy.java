package de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.spam.strategies;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;

public interface SpamDetectionStrategy {
  boolean isSpam(MailboxRepository mailboxRepository, Email email);

  String getHumanReadableReason();
}
