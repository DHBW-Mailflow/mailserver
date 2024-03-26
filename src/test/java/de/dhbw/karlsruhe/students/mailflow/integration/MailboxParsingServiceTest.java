package de.dhbw.karlsruhe.students.mailflow.integration;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingServiceException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxDoesNotExistException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.reader.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MailboxParsingServiceTest {

  @Test
  void testNonExistentMailboxThrows(@TempDir File tempDir) {
    // Arrange
    Address mailboxOwner = new Address("someUser", "someDomain.de");
    MailboxRepository mockedRepository = new LocalMailboxRepository(tempDir);
    MailboxParser mockedParser = content -> null;
    MailboxParsingService service = new MailboxParsingService(mockedRepository, mockedParser);

    // Assert
    Assertions.assertThrows(
        // Act
        MailboxDoesNotExistException.class, () -> service.getMailboxOfAddress(mailboxOwner));
  }

  @Test
  void existentMailboxCorrectlyProvided(@TempDir File tempDir)
      throws MailboxParsingServiceException, IOException, MailboxDoesNotExistException {
    // Arrange
    Address mailboxOwner = new Address("someUser", "someDomain.de");
    File userDirectory = new File(tempDir, mailboxOwner.toString());
    userDirectory.mkdirs();
    File existingMailboxFile = new File(userDirectory, "read.json");
    boolean successFullyCreated = existingMailboxFile.createNewFile();

    MailboxRepository mockedRepository = new LocalMailboxRepository(tempDir);

    MailboxParser mockedParser =
        content -> Mailbox.create(mailboxOwner, List.of(), MailboxType.READ);
    MailboxParsingService service = new MailboxParsingService(mockedRepository, mockedParser);

    // Act
    Mailbox mailbox = service.getMailboxOfAddress(mailboxOwner);

    // Assert
    Assertions.assertEquals(mailbox.getEmails(), List.of());
    Assertions.assertEquals(mailbox.getOwner(), mailboxOwner);
    // only MailboxType.READ is currently supported in the application layer
    Assertions.assertEquals(MailboxType.READ, mailbox.getType());
  }
}
