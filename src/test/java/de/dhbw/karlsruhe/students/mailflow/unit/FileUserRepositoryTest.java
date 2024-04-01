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

  private FileUserRepository fileUserRepository;

  @BeforeEach
  public void setUp() {
    String testFilePath = "save_users.json";
    fileUserRepository = new FileUserRepository(testFilePath);
  }

  @Test
  public void testUserIsAlreadyRegistered() throws SaveUserException, HashingFailedException {
    // Arrange
    fileUserRepository.clearUsers();
    Address email = new Address("test", "example.com");
    String password = "password";
    String salt = "salt";
    User user = new User(email, password, salt);


    // Act
    fileUserRepository.registerUser(user);

    // Assert
    SaveUserException thrown = assertThrows(
        SaveUserException.class,
        () -> fileUserRepository.registerUser(user),
        "Expected registerUser() to throw, but it didn't"
    );

    assertEquals("User is already registered", thrown.getMessage());
  }

}
