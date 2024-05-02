package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecipientsTest {

  @Test
  void testNullValues() {
    Recipients recipients = new Recipients(null, null, null);
    assertEquals(List.of(), recipients.to());
    assertEquals(List.of(), recipients.cc());
    assertEquals(List.of(), recipients.bcc());
  }

  @Test
  void testWithoutBCC() {
    // Arrange
    Address addressTo = new Address("some", "domain.de");
    Address addressCC = new Address("another", "domain.de");
    Address addressBCC = new Address("any", "domain.de");

    Recipients recipients =
        new Recipients(List.of(addressTo), List.of(addressCC), List.of(addressBCC));
    // Act
    Recipients noBCC = recipients.withoutBCCRecipients();

    // Assert
    assertEquals(List.of(addressTo), noBCC.to());
    assertEquals(List.of(addressCC), noBCC.cc());
    assertEquals(List.of(), noBCC.bcc());
  }
}
