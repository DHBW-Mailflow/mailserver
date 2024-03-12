package de.dhbw.karlsruhe.students.mailflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingServiceException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.CreateEmailHelper;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.MboxParser;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ParseMboxTest {

  @Mock
  private Session sessionMock;

  @Mock
  private Folder folderMock;

  @Mock
  private Message messageMock;

  @Mock
  private Email emailMock;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testParsedMailboxSuccess() throws MessagingException {
    // Arrange
    List<Message> messageList = new ArrayList<>();
    messageList.add(messageMock);
    when(folderMock.getMessages()).thenReturn(messageList.toArray(new Message[0]));
    when(CreateEmailHelper.createEmailWithMessage(messageMock)).thenReturn(emailMock);

    MailboxParser parser = new MboxParser();

    File mailboxFile = new File("Daily_Problem.mbox");
    Address address = new Address("test", "gmail.com");

    // Act
    Mailbox mailbox = parser.parseMailbox(mailboxFile, address);

    // Assert (Verify expected behavior)
    assertEquals(1, mailbox.getEmails().size());
    verify(sessionMock, times(1)).getInstance(System.getProperties(), null);
    verify(folderMock, times(1)).open(Folder.READ_ONLY);
  }
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
