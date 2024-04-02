package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.FileUserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import java.io.File;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileUserRepositoryTest {

  private final FileUserRepository fileUserRepository = new FileUserRepository();

  @Test
  void testUserIsAlreadyRegistered() throws SaveUserException, HashingFailedException {
    // Arrange
    fileUserRepository.clearUsers();
    Address email = new Address("test", "example.com");
    String password = "password";

    // Act
    fileUserRepository.registerUser(email, password);

    // Assert
    SaveUserException thrown =
        assertThrows(
            SaveUserException.class,
            () -> fileUserRepository.registerUser(email, password),
            "Expected registerUser() to throw, but it didn't");

    assertEquals("User is already registered", thrown.getMessage());
  }

  @Test
  void testFindByEmailAndPassword() throws SaveUserException, HashingFailedException {
    // Arrange
    fileUserRepository.clearUsers();
    Address email = new Address("test", "example.com");
    String password = "password";
    fileUserRepository.registerUser(email, password);

    // Act
    Optional<User> retrievedUser = fileUserRepository.findByEmailAndPassword(email, password);

    // Assert
    assertTrue(retrievedUser.isPresent(), "Expected a user to be present");
    assertEquals(email, retrievedUser.get().email(), "Expected the emails to match");

    // Arrange
    Address wrongEmail = new Address("wrong", "example.com");

    // Act
    Optional<User> nonExistentUser =
        fileUserRepository.findByEmailAndPassword(wrongEmail, password);

    // Assert
    assertFalse(nonExistentUser.isPresent(), "Expected no user to be present");
  }

}
