package de.dhbw.karlsruhe.students.mailflow.integration;

import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jonas-Karl
 */
class AuthServiceTest {

  @Test
  void shouldLoginSuccessfully() throws AuthorizationException, LoadingUsersException {
    // Arrange
    User userToLogin = new User(new Address("test", "example.de"), "password", "salt");
    UserRepository mockedUserRepository =
        new UserRepository() {
          @Override
          public Optional<User> findByEmail(Address email) {
            return Optional.of(userToLogin);
          }

          @Override
          public boolean save(User user) {
            // Not needed for this test
            return true;
          }
        };

    var loginService = new AuthService(mockedUserRepository, (password, user) -> true);

    loginService.login(userToLogin.email().toString(), userToLogin.password());

    Assertions.assertTrue(loginService.isLoggedIn());
    assertEquals(userToLogin.email(), loginService.getSessionUserAddress());
  }

  @Test
  void shouldNotLoginSuccessfully() {
    // Arrange
    User userToLogin = new User(new Address("someUser", "example.de"), "somePassword", "salt");
    User otherUser = new User(new Address("anotherUser", "example.de"), "anotherPassword", "salt");
    UserRepository mockedUserRepository =
        new UserRepository() {
          @Override
          public Optional<User> findByEmail(Address email) {
            return Optional.of(otherUser);
          }

          @Override
          public boolean save(User user) {
            // Not needed for this test
            return true;
          }
        };
    PasswordChecker mockedPasswordChecker = (password, user) -> false;

    var loginService = new AuthService(mockedUserRepository, mockedPasswordChecker);
    // Assert
    Assertions.assertThrows(
        AuthorizationException.class,
        () -> {
          // Act
          loginService.login(otherUser.email().toString(), userToLogin.password());
        });
  }
}
