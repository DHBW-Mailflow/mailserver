package de.dhbw.karlsruhe.students.mailflow.integration;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegistrationService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserCreator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {

  @Test
  void registerNewUser() {
    // Arrange
    User notYetRegisteredUser = new User(new Address("test", "example.de"), "password", "salt");
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
                notYetRegisteredUser.email(), notYetRegisteredUser.password()));
  }

  @Test
  void registerExistingUser() {
    // Arrange
    User alreadyRegisteredUser = new User(new Address("test", "example.de"), "password", "salt");
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
                alreadyRegisteredUser.email(), alreadyRegisteredUser.password()));
  }
}
