package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import java.io.File;

public interface MailboxConverter {
  Mailbox deserializeMailboxFile(File mailboxFile) throws MailboxLoadingException;

  String serializeMailbox(Mailbox mailbox);
}
