package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

  @Test
  void testWithoutBCCRecipients() {

    Address sender = new Address("some", "domain.de");
    Address recipientBCC = new Address("someBCC", "domain.de");
    Address recipientCC = new Address("someCC", "domain.de");
    Address recipientTO = new Address("someTO", "domain.de");
    Subject subject = new Subject("SomeSubject");
    Recipients inputRecipients =
        new Recipients(List.of(recipientTO), List.of(recipientCC), List.of(recipientBCC));
    Recipients expectedRecipients =
        new Recipients(List.of(recipientTO), List.of(recipientCC), List.of());
    EmailMetadata emailMetadata =
        new EmailMetadata(subject, sender, List.of(), inputRecipients, SentDate.ofNow());
    Email withoutBCCRecipients = Email.create("someContent", emailMetadata).withoutBCCRecipients();
    Assertions.assertEquals(withoutBCCRecipients.getRecipientTo(), expectedRecipients.to());
    Assertions.assertEquals(withoutBCCRecipients.getRecipientTo(), inputRecipients.to());
    Assertions.assertEquals(withoutBCCRecipients.getRecipientCC(), expectedRecipients.cc());
    Assertions.assertEquals(withoutBCCRecipients.getRecipientCC(), inputRecipients.cc());

    Assertions.assertNotEquals(withoutBCCRecipients.getRecipientBCC(), inputRecipients.bcc());
    Assertions.assertEquals(withoutBCCRecipients.getRecipientBCC(), expectedRecipients.bcc());
  }
}
