package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalPasswordChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jonas-Karl
 */
class LocalPasswordCheckerTest {

  @Test
  void nullValuesShouldNotThrow() {
    // Arrange
    var localPasswordAuthenticator = new LocalPasswordChecker();
    User user = new User(new Address("test", "example.de"), "password", "salt");

    // Assert
    Assertions.assertDoesNotThrow(
        () -> {
          // Act
          localPasswordAuthenticator.checkPassword(null, null);
          localPasswordAuthenticator.checkPassword("password", null);
          localPasswordAuthenticator.checkPassword(null, user);
        });
  }

  @Test
  void testNullValuesShouldReturnFalse() {
    // Arrange
    var localPasswordAuthenticator = new LocalPasswordChecker();
    User user = new User(new Address("test", "example.de"), "hashedPassword", "salt");

    // Act
    var test1 = localPasswordAuthenticator.checkPassword(null, null);
    var test2 = localPasswordAuthenticator.checkPassword("unhashedPassword", null);
    var test3 = localPasswordAuthenticator.checkPassword(null, user);

    // Assert
    Assertions.assertFalse(test1);
    Assertions.assertFalse(test2);
    Assertions.assertFalse(test3);
  }

  @Test
  void testShouldCheckCorrectlyWithSHA256() {
    // Arrange
    var localPasswordAuthenticator = new LocalPasswordChecker();
    User user =
        new User(
            new Address("test", "example.de"),
            "2f89b50dbdb72487c468518cfc411c2b6adf0645d59b99023a239640ccc561dc",
            "exampleSalt");

    // Act
    var correctPassword = localPasswordAuthenticator.checkPassword("password", user);

    // Assert
    Assertions.assertTrue(correctPassword);
  }
}
