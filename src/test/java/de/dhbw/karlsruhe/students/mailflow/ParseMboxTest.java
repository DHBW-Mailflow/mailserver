package de.dhbw.karlsruhe.students.mailflow;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingServiceException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.MboxParser;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.MboxParser.FolderProvider;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.MboxParser.SessionProvider;
import java.io.File;
import java.util.Optional;
import org.junit.jupiter.api.Test;


public class ParseMboxTest {


  @Test
  public void mboxParsingException(){

  }
  @Test
  public void parseMboxFileToMailbox() throws MailboxParsingServiceException {

    // Arrange

    SessionProvider mockSessionProvider = new MockSessionProvider();
    FolderProvider mockFolderProvider = new MockFolderProvider();

    MboxParser mboxParser = new MboxParser(mockSessionProvider, mockFolderProvider);
    File file = new File("Daily_Problem.mbox");

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
