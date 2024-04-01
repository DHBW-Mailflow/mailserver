package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import org.junit.jupiter.api.Test;

public class UserTest {

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

}
