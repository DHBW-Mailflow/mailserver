package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.InvalidEmailException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.InvalidPasswordException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.InvalidSaltException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void testUserCreation() {
    // Arrange
    Address email = new Address("test", "example.com");
    String password = "password";
    String salt = "salt";

    // Act
    User user = new User(email, password, salt);

    // Assert
    assertEquals(email, user.email());
    assertEquals(password, user.password());
    assertEquals(salt, user.salt());
  }

  @Test
  void testUserCreationWithInvalidEmail() {
    // Arrange
    Address email = null;
    String password = "password";
    String salt = "salt";

    // Act

    // Assert
    assertThrows(InvalidEmailException.class, () -> new User(email, password, salt));
  }

  @Test
  void testUserCreationWithInvalidPassword() {
    // Arrange
    Address email = new Address("test", "example.com");
    String password = null;
    String salt = "salt";

    // Act

    // Assert
    assertThrows(InvalidPasswordException.class, () -> new User(email, password, salt));
  }

  @Test
  void testUserCreationWithInvalidSalt() {
    // Arrange
    Address email = new Address("test", "example.com");
    String password = "password";
    String salt = null;

    // Act

    // Assert
    assertThrows(InvalidSaltException.class, () -> new User(email, password, salt));
  }
}
