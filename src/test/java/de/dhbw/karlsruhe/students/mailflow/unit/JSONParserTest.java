package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.fest.assertions.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.JSONMailboxParser;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JSONParserTest {
  @Test
  public void successfullyParseJsonToMailbox() {
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
            new SentDate(Date.from(Instant.parse("2024-03-20T11:02:50.00Z"))));

    String emailContent = "someContent";

    // Arrange
    // This string was automatically generated by the MboxGenerator class.
    // Currently, it is not in this branch
    String jsonString =
        """
            {
              "address": {
                "localPart": "anotherUser",
                "domain": "anotherDomain.de"
              },
              "emails": [
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
                      "date": "Mar 20, 2024, 11:02:50 AM"
                    }
                  },
                  "isRead": false,
                  "content": "someContent",
                  "attachments": [],
                  "id": {
                    "id": "81ef6faa-86bd-441e-b76a-3d37c7227d31"
                  }
                }
              ],
              "type": "COMMON",
              "id": {
                "id": "2d1e184a-6120-4d15-918b-05c38382a4f9"
              }
            }""";

    // Act
    JSONMailboxParser parser = new JSONMailboxParser();
    Mailbox mailbox = parser.parseMailbox(jsonString);

    // Assert
    assertThat(mailbox).isNotNull();
    assertThat(mailbox.getType()).isEqualsToByComparingFields(MailboxType.COMMON);
    assertThat(mailbox.getEmails()).hasSize(1);

    // everything with getters including the id will be compared. Only the ids should be different
    assertThat(mailbox.getEmails().get(0))
        .isLenientEqualsToByIgnoringFields(
            Email.create(emailContent, emailMetadata), "emailMetadata", "id");
    assertThat(mailbox.getEmails().get(0).getEmailMetadata())
        .isLenientEqualsToByIgnoringFields(emailMetadata, "id");
  }

  @Test
  public void parsingMalformedJsonShouldThrow() {
    // Arrange
    String invalidJsonString =
        """
            {
            malformed,,,
              "address": {
                "localPart": "anotherUser",
                "domain": "anotherDomain.de"
              },
              "emails": [],
              "type": "COMMON",
              "id": {
                "id": "2d1e184a-6120-4d15-918b-05c38382a4f9"
              }
            }""";

    // Act
    JSONMailboxParser parser = new JSONMailboxParser();

    // Assert
    Assertions.assertThrows(
        // Act
        MailboxParsingException.class, () -> parser.parseMailbox(invalidJsonString));
  }
}