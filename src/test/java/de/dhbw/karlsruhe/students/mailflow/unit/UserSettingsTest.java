package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserSettingsTest {
  @Test
  void testEquals() {
    // Arrange
    Address address = new Address("some", "domain.de");
    String previousSignature = "Signature1";
    String newSignature = "Signature2";
    UserSettings userSettings = new UserSettings(address, previousSignature);
    UserSettings otherUserSettings = new UserSettings(address, newSignature);

    // Act
    Set<UserSettings> userSettingsSet = new HashSet<>();
    userSettingsSet.add(userSettings);
    userSettingsSet.add(otherUserSettings);

    // Assert
    Assertions.assertEquals(1, userSettingsSet.size());
    Assertions.assertEquals(userSettings, otherUserSettings);
    Assertions.assertEquals(userSettings.hashCode(), otherUserSettings.hashCode());
  }
}
