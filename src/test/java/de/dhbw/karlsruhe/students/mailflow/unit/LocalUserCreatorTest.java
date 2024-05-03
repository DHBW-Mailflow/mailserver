package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.InvalidEmailException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LocalUserCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jonas-Karl
 */
class LocalUserCreatorTest {

  @Test
  void testCreateUserWithInvalidCredentials() {
    var localUserAuthenticator = new LocalUserCreator();
    Assertions.assertThrows(
        InvalidEmailException.class, () -> localUserAuthenticator.createUser(null, null));
  }

  @Test
  void testCreateUser() throws HashingFailedException {
    User expectedUser = new User(new Address("test", "example.de"), "password", "salt");

    var localUserAuthenticator = new LocalUserCreator();
    User user =
        localUserAuthenticator.createUser(expectedUser.getAddress(), expectedUser.getPassword());
    assertThat(user).isLenientEqualsToByIgnoringFields(expectedUser, "password", "salt");
    assertFalse(user.getPassword().isEmpty());
  }
}
