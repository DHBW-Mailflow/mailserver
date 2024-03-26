package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;

public interface MailboxRepository {
  File provideStoredMailboxFileFor(Address userAddress, MailboxType type)
      throws MailboxDoesNotExistException;

  File saveMailbox(Mailbox mailbox) throws MailboxCreationException, MailboxWritingException;
}
