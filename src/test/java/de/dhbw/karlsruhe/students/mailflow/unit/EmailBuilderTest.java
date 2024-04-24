package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.EmailBuilder;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailBuilderTest {

  @Test
  void testMinimalBuild() {
    EmailBuilder emailBuilder = new EmailBuilder();
    Email email = emailBuilder.build();

    Assertions.assertNotNull(email);
    Assertions.assertNotNull(email.getContent());
    Assertions.assertNotNull(email.getSubject());
    Assertions.assertNotNull(email.getSendDate());
    Assertions.assertNotNull(email.getRecipientTo());
    Assertions.assertNotNull(email.getRecipientBCC());
    Assertions.assertNotNull(email.getRecipientCC());
    Assertions.assertNotNull(email.getAttachments());
    Assertions.assertNull(email.getSender());
  }

  @Test
  void testMinimalBuildWithSender() {
    EmailBuilder emailBuilder = new EmailBuilder();
    Address sender = new Address("some", "domain.de");
    Email email = emailBuilder.withSender(sender).build();

    Assertions.assertNotNull(email);
    Assertions.assertNotNull(email.getContent());
    Assertions.assertNotNull(email.getSubject());
    Assertions.assertNotNull(email.getSendDate());
    Assertions.assertNotNull(email.getRecipientTo());
    Assertions.assertNotNull(email.getRecipientBCC());
    Assertions.assertNotNull(email.getRecipientCC());
    Assertions.assertNotNull(email.getAttachments());
    Assertions.assertNotNull(email.getSender());
    Assertions.assertEquals(email.getSender(), sender);
  }

  @Test
  void testOverwritingMetadata() {
    EmailBuilder emailBuilder = new EmailBuilder();
    Address metadataSender = new Address("some", "domain.de");
    Recipients metadataRecipients = new Recipients(List.of(), List.of(metadataSender), List.of());
    Subject metaDataSubject = new Subject("MetaDataSubject");
    EmailMetadata metadata =
        new EmailMetadata(
            metaDataSubject, metadataSender, List.of(), metadataRecipients, SentDate.ofNow());

    String builderContent = "builder content";
    Address builderSender = new Address("another", "domain.de");
    Email email =
        emailBuilder
            .withMetaData(metadata)
            .withSender(builderSender)
            .withContent(builderContent)
            .build();

    Assertions.assertNotNull(email);
    Assertions.assertNotNull(email.getContent());
    Assertions.assertEquals(email.getContent(), builderContent);
    Assertions.assertNotNull(email.getSubject());
    Assertions.assertNotNull(email.getSendDate());
    Assertions.assertNotNull(email.getRecipientTo());
    Assertions.assertEquals(email.getRecipientTo(), metadataRecipients.to());
    Assertions.assertEquals(email.getRecipientTo(), metadataRecipients.to());
    Assertions.assertNotNull(email.getRecipientBCC());
    Assertions.assertNotNull(email.getRecipientCC());
    Assertions.assertNotNull(email.getAttachments());
    Assertions.assertNotNull(email.getSender());
    Assertions.assertNotEquals(email.getSender(), metadataSender);
    Assertions.assertEquals(email.getSender(), builderSender);
  }
}
