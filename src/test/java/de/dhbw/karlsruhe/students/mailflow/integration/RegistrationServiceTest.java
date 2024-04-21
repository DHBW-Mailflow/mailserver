package de.dhbw.karlsruhe.students.mailflow.integration;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegistrationService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserCreator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jonas-Karl
 */
class RegistrationServiceTest {

  @Test
  void registerNewUser() {
    // Arrange
    UserSettings userSettings = new UserSettings("settings");
    User notYetRegisteredUser = new User(new Address("test", "example.de"), "password", "salt", userSettings);
    var mockedUserRepository =
        new UserRepository() {
          @Override
          public Optional<User> findByEmail(Address email) {
            return Optional.empty();
          }

          @Override
          public boolean save(User user) {
            return true;
          }
        };

    var mockedUserAuthenticator =
        new UserCreator() {
          @Override
          public User createUser(Address email, String password) {
            return notYetRegisteredUser;
          }
        };

    var registerService = new RegistrationService(mockedUserRepository, mockedUserAuthenticator);

    // Assert
    Assertions.assertDoesNotThrow(
        // Act
        () ->
            registerService.register(
                notYetRegisteredUser.email().toString(), notYetRegisteredUser.password()));
  }

  @Test
  void registerExistingUser() {
    // Arrange
    UserSettings userSettings = new UserSettings("Settings");
    User alreadyRegisteredUser = new User(new Address("test", "example.de"), "password", "salt", userSettings);
    var mockedUserRepository =
        new UserRepository() {
          @Override
          public Optional<User> findByEmail(Address email) {
            return Optional.of(alreadyRegisteredUser);
          }

          @Override
          public boolean save(User user) {
            return true;
          }
        };

    var mockedUserAuthenticator =
        new UserCreator() {
          @Override
          public User createUser(Address email, String password) {
            return alreadyRegisteredUser;
          }
        };

    var registerService = new RegistrationService(mockedUserRepository, mockedUserAuthenticator);

    // Assert
    Assertions.assertThrows(
        AuthorizationException.class,
        // Act
        () ->
            registerService.register(
                alreadyRegisteredUser.email().toString(), alreadyRegisteredUser.password()));
  }
}
