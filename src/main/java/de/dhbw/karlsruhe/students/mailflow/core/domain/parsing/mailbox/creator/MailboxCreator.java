package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.creator;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public interface MailboxCreator {

  String generateMailboxContent(Mailbox mailbox);
}
