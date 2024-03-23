package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.generator.FileCreationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.generator.FileWritingException;
import java.io.File;
import java.util.Optional;

public interface MailboxRepository {
  Optional<File> provideStoredMailboxFileFor(Address userAddress, MailboxType type);

  File saveMailbox(Mailbox mailbox) throws FileCreationException, FileWritingException;
}
