package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.fest.assertions.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.JSONMailboxConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author Jonas-Karl
 */
class JSONMailboxConverterTest {

  private File tempDir;

  @BeforeEach
  void setUp(@TempDir File tempDir) {
    this.tempDir = tempDir;
  }

  @Test
  void successfullyParseJsonToMailbox() throws MailboxLoadingException, IOException {
    // Arrange
    File fileToParse = new File(tempDir, "test.json");
    Address owner = new Address("anotherUser", "anotherDomain.de");
    Address toRecipient = new Address("someTORecipient", "someDomain.de");
    Address ccRecipient = new Address("anotherCCRecipient", "someDomain.de");
    Address bccRecipient = new Address("someBCCRecipient", "someDomain.de");

    EmailMetadata emailMetadata =
        new EmailMetadata(
            new Subject("some Subject"),
            owner,
            List.of(new Header("someHeader", "someValue")),
            new Recipients(List.of(toRecipient), List.of(ccRecipient), List.of(bccRecipient)),
            SentDate.ofFormattedString("2024-03-21T00:30:38.8095474Z"));
    String emailContent = "someContent";

    Mailbox expectedMailbox = Mailbox.create(owner, Map.of(), MailboxType.INBOX);
    Email expectedEmail = Email.create(emailContent, emailMetadata);
    String jsonString =
        """
        {
          "address": {
            "localPart": "anotherUser",
            "domain": "anotherDomain.de"
          },
          "emails": [
            [
              {
                "emailMetadata": {
                  "subject": {
                    "subject": "some Subject"
                  },
                  "sender": {
                    "localPart": "anotherUser",
                    "domain": "anotherDomain.de"
                  },
                  "headers": [
                    {
                      "name": "someHeader",
                      "value": "someValue"
                    }
                  ],
                  "recipients": {
                    "to": [
                      {
                        "localPart": "someTORecipient",
                        "domain": "someDomain.de"
                      }
                    ],
                    "cc": [
                      {
                        "localPart": "anotherCCRecipient",
                        "domain": "someDomain.de"
                      }
                    ],
                    "bcc": [
                      {
                        "localPart": "someBCCRecipient",
                        "domain": "someDomain.de"
                      }
                    ]
                  },
                  "sentDate": {
                    "date": "2024-03-21T00:30:38.809547400"
                  }
                },
                "content": "someContent",
                "attachments": [],
                "id": {
                  "id": "7c7f2c8f-797c-400c-95f1-930d2b5b8901"
                }
              },
              [
                "UNREAD"
              ]
            ]
          ],
          "type": "INBOX",
          "id": {
            "id": "646c6639-6af6-49a8-aa51-a5faff4a4a09"
          }
        }
        """;
    Files.writeString(fileToParse.toPath(), jsonString);

    // Act
    JSONMailboxConverter parser = new JSONMailboxConverter();
    Mailbox mailbox = parser.deserializeMailboxFile(fileToParse);

    // Assert
    assertThat(mailbox)
        .isNotNull()
        .isLenientEqualsToByIgnoringFields(expectedMailbox, "id", "emails");
    assertThat(mailbox.getEmailList()).hasSize(1);
    assertThat(mailbox.getEmailList().getFirst())
        .isLenientEqualsToByIgnoringFields(expectedEmail, "id");
  }

  @Test
  void parsingMalformedJsonShouldThrow() throws IOException {
    // Arrange
    File fileToParse = new File(tempDir, "test.json");
    String invalidJsonString =
        """
        {
        malformed,,,
          "address": {
            "localPart": "anotherUser",
            "domain": "anotherDomain.de"
          },
          "emails": [],
          "type": "READ",
          "id": {
            "id": "2d1e184a-6120-4d15-918b-05c38382a4f9"
          }
        }""";

    Files.writeString(fileToParse.toPath(), invalidJsonString);
    // Act
    JSONMailboxConverter parser = new JSONMailboxConverter();

    // Assert
    Assertions.assertThrows(
        // Act
        MailboxLoadingException.class, () -> parser.deserializeMailboxFile(fileToParse));
  }

  @Test
  void serializeAndDeserializeComplexMailbox() throws IOException, MailboxLoadingException {
    // Arrange
    File fileToParse = new File(tempDir, "test.json");
    Address owner = new Address("anotherUser", "anotherDomain.de");
    Address toRecipient = new Address("someTORecipient", "someDomain.de");
    Address ccRecipient = new Address("anotherCCRecipient", "someDomain.de");
    Address bccRecipient = new Address("someBCCRecipient", "someDomain.de");

    EmailMetadata emailMetadata =
        new EmailMetadata(
            new Subject("some Subject"),
            owner,
            List.of(new Header("someHeader", "someValue")),
            new Recipients(List.of(toRecipient), List.of(ccRecipient), List.of(bccRecipient)),
            SentDate.ofFormattedString("2024-03-21T00:30:38.8095474Z"));
    String emailContent = "someContent";
    Email expectedEmail = Email.create(emailContent, emailMetadata);

    Label label = Label.UNREAD;
    Mailbox expectedMailbox =
        Mailbox.create(owner, Map.of(expectedEmail, Set.of(label)), MailboxType.INBOX);
    JSONMailboxConverter parser = new JSONMailboxConverter();
    // Act
    String serializedJSON = parser.serializeMailbox(expectedMailbox);

    Files.writeString(fileToParse.toPath(), serializedJSON);
    Mailbox deserializedJSON = parser.deserializeMailboxFile(fileToParse);
    // Assert
    Assertions.assertEquals(expectedMailbox, deserializedJSON);
  }
}
