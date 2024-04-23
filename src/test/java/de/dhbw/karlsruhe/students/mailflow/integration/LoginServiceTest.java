package de.dhbw.karlsruhe.students.mailflow.integration;

import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSession;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jonas-Karl
 */
class LoginServiceTest {

  @Test
  void shouldLoginSuccessfully()
      throws AuthorizationException, LoadingUsersException, SaveUserException {
    // Arrange
    final User userToLogin = new User(new Address("test", "example.de"), "password", "salt");
    final UserRepository mockedUserRepository =
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
    final AuthSession authSession = new AuthSession();

    final var loginService =
        new LoginService(authSession, mockedUserRepository, (password, user) -> true);

    loginService.login(userToLogin.email(), userToLogin.password());

    Assertions.assertTrue(authSession.isLoggedIn());
    assertEquals(userToLogin.email(), authSession.getSessionUserAddress());
  }

  @Test
  void shouldNotLoginSuccessfully() {
    // Arrange
    final User userToLogin =
        new User(new Address("someUser", "example.de"), "somePassword", "salt");
    final User otherUser =
        new User(new Address("anotherUser", "example.de"), "anotherPassword", "salt");
    final UserRepository mockedUserRepository =
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
    final PasswordChecker mockedPasswordChecker = (password, user) -> false;

    final AuthSession authSession = new AuthSession();

    final var loginService =
        new LoginService(authSession, mockedUserRepository, mockedPasswordChecker);
    // Assert
    Assertions.assertThrows(
        AuthorizationException.class,
        () -> {
          // Act
          loginService.login(otherUser.email(), userToLogin.password());
        });
  }
}
