package de.dhbw.karlsruhe.students.mailflow.integration;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingServiceException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MailboxParsingServiceTest {

  @Test
  public void testNonExistentMailboxThrows() {
    // Arrange
    Address mailboxOwner = new Address("someUser", "someDomain.de");
    MailboxRepository mockedRepository = (userAddress, type) -> Optional.empty();
    MailboxParser mockedParser = content -> null;
    MailboxParsingService service = new MailboxParsingService(mockedRepository, mockedParser);

    // Assert
    Assertions.assertThrows(

        // Act
        MailboxParsingServiceException.class, () -> service.getMailboxOfAddress(mailboxOwner));
  }

  @Test
  public void existentMailboxCorrectlyProvided(@TempDir File tempDir)
      throws MailboxParsingServiceException, IOException {
    // Arrange
    Address mailboxOwner = new Address("someUser", "someDomain.de");
    File justAnExistingFile = new File(tempDir, "mailboxFile.json");
    boolean successFullyCreated = justAnExistingFile.createNewFile();

    MailboxRepository mockedRepository = (userAddress, type) -> Optional.of(justAnExistingFile);
    MailboxParser mockedParser =
        content -> Mailbox.create(mailboxOwner, List.of(), MailboxType.COMMON);
    MailboxParsingService service = new MailboxParsingService(mockedRepository, mockedParser);

    // Act
    Mailbox mailbox = service.getMailboxOfAddress(mailboxOwner);

    // Assert
    Assertions.assertEquals(mailbox.getEmails(), List.of());
    Assertions.assertEquals(
        mailbox.getType(),
        MailboxType
            .COMMON); // only MailboxType.COMMON is currently supported in the application layer
    Assertions.assertEquals(mailbox.getOwner(), mailboxOwner);
  }
}