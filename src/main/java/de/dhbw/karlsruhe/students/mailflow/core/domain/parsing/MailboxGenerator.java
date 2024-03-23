package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public interface MailboxGenerator {

  String generateMailboxContent(Mailbox mailbox);
}
