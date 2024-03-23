package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.generator;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public interface MailboxGenerator {

  String generateMailboxContent(Mailbox mailbox);
}
