package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

public class FileUserRepositoryTest {

  private FileUserRepository fileUserRepository;

  @BeforeEach
  public void setUp() {
    String testFilePath = "src/test/resources/test_users.json";
    fileUserRepository = new FileUserRepository(testFilePath);
  }

  @Test
  public void testRegisterUsers() throws SaveUserException, HashingFailedException {
    // Arrange
    Address email = new Address("test", "example.com");
    String password = "password";
    String salt = "salt";
    User user = new User(email, password, salt);

    // Act
    fileUserRepository.registerUser(user);

    // Assert
    Optional<User> retrievedUser = fileUserRepository.findByEmailAndPassword(email, password);
    assertTrue(retrievedUser.isPresent());
    assertEquals(user, retrievedUser.get());
  }

  @Test
  public void testFindByEmailAndPassword() throws HashingFailedException, SaveUserException {
    // Arrange
    Address email = new Address("test", "example.com");
    String password = "password";
    String salt = "salt";
    User user = new User(email, password, salt);

    fileUserRepository.registerUser(user);

    Optional<User> retrievedUser = fileUserRepository.findByEmailAndPassword(email, password);
    assertTrue(retrievedUser.isPresent());
    assertEquals(user, retrievedUser.get());

    Optional<User> nonExistentUser =
        fileUserRepository.findByEmailAndPassword(
            new Address("nonexistent", "example.com"), password);
    assertFalse(nonExistentUser.isPresent());

    Optional<User> incorrectPasswordUser =
        fileUserRepository.findByEmailAndPassword(email, "incorrectPassword");
    assertFalse(incorrectPasswordUser.isPresent());
    }
}
