package de.dhbw.karlsruhe.students.mailflow;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingServiceException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.MboxParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ParseMboxTest {

  @Test
  public void parseMboxFileToMailbox() throws MailboxParsingServiceException {

    // Arrange
    File file = new File("Daily_Problem.mbox");

    MboxParser mboxParser = new MboxParser();

    Address address = new Address("test", "gmail.com");

    MailboxRepository mailboxRepository = new MailboxRepository() {
      @Override
      public Optional<File> provideStoredMailboxFileFor(Address userAddress) {
        return Optional.of(file);
      }
    };

    MailboxParsingService mailboxParsingService = new MailboxParsingService(mailboxRepository,mboxParser);

    // Act

    // Service (use case)
    Mailbox mailbox = mailboxParsingService.getMailboxOfAddress(address);


    // Parser


    // Assert
    assertNotNull(mailbox);

  }

  @Test
  public void getMailboxOfAddress() throws MailboxParsingServiceException {
    // Arrange
    Address address = new Address("test", "gmail.com");
    MailboxRepository mailboxRepository = new LocalMailboxRepository();
    MboxParser mboxParser = new MboxParser();
    MailboxParsingService mailboxParsingService = new MailboxParsingService(mailboxRepository, mboxParser);

    // Act
    Mailbox mailbox = mailboxParsingService.getMailboxOfAddress(address);

    // Assert
    assertNotNull(mailbox);
  }

}
