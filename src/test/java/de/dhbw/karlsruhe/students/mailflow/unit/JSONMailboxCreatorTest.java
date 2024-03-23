package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.JSONMailboxCreator;
import java.util.List;
import org.junit.jupiter.api.Test;

class JSONMailboxCreatorTest {


  @Test
  void parseMailboxToJsonTest() {
    // Arrange
    Mailbox mbox = createBasicMailboxObject();

    // Act
    String result = new JSONMailboxCreator().generateMailboxContent(mbox);

    System.out.println(result);

    // Assert
    assertNotNull(result);
    assertThat(result)
        .contains("someUser")
        .contains("someDomain.de")
        .contains("someTORecipient")
        .contains("anotherCCRecipient")
        .contains("anotherDomain.de")
        .contains("someBCCRecipient")
        .contains("some Subject")
        .contains("someHeader")
        .contains("someValue")
        .contains("2024-01-01T00:00")
        .contains("id")
        .contains("READ");

    // Json object
    assertThat(result).startsWith("{").endsWith("}");
  }

  private Mailbox createBasicMailboxObject() {
    Address owner = new Address("someUser", "someDomain.de");
    Address toRecipient = new Address("someTORecipient", "someDomain.de");
    Address ccRecipient = new Address("anotherCCRecipient", "anotherDomain.de");
    Address bccRecipient = new Address("someBCCRecipient", "someDomain.de");
    Recipients recipients =
        new Recipients(List.of(toRecipient), List.of(ccRecipient), List.of(bccRecipient));

    EmailMetadata emailMetadata =
        new EmailMetadata(
            new Subject("some Subject"),
            owner,
            List.of(new Header("someHeader", "someValue")),
            recipients,
            SentDate.ofFormattedString("2024-01-01T00:00:00Z"));

    List<Email> emails = List.of(Email.create("someContent", emailMetadata));
    return Mailbox.create(owner, emails, MailboxType.READ);
  }
}
