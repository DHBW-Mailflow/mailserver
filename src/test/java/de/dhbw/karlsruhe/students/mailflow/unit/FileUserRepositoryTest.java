package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.FileUserRepository;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author seiferla, Jonas-Karl
 */
class FileUserRepositoryTest {

  private FileUserRepository fileUserRepository;
  private File registeredUsersFile;

  @BeforeEach
  void setUp(@TempDir File tempDir) throws IOException {
    registeredUsersFile = new File(tempDir, "registered-users.json");
    registeredUsersFile.createNewFile();
    this.fileUserRepository = new FileUserRepository(registeredUsersFile);
  }

  @Test
  void nonExistentFileShouldBeCreated() throws LoadingUsersException, SaveUserException {
    // Arrange
    // overwrite setUp method for this test
    File file = new File(registeredUsersFile.getParentFile(), "nonExistentFile.json");
    fileUserRepository = new FileUserRepository(file);

    Address randomEmailAddress = new Address("test", "test.de");

    // Act
    fileUserRepository.findByEmail(randomEmailAddress);

    // Assert
    assertTrue(file.exists());
  }

  @Test
  void testSaveSingleUser() throws Exception {
    // Arrange
    final User user = new User(new Address("test", "test.de"), "password", "salt");

    final String exceptedContent =
        "[{\"email\":{\"localPart\":\"test\",\"domain\":\"test.de\"},\"password\":\"password\",\"salt\":\"salt\"}]";

    // Act
    fileUserRepository.save(user);

    // Assert
    final String fileContent =
        Files.readString(registeredUsersFile.toPath(), StandardCharsets.UTF_8);
    assertEquals(exceptedContent, fileContent);
  }

  @Test
  void testMultipleSameUsers() throws Exception {
    // Arrange
    final User user = new User(new Address("test", "test.de"), "password", "salt");

    final String exceptedContent =
        "[{\"email\":{\"localPart\":\"test\",\"domain\":\"test.de\"},\"password\":\"password\",\"salt\":\"salt\"}]";

    // Act
    fileUserRepository.save(user);
    fileUserRepository.save(user);

    // Assert
    final String fileContent =
        Files.readString(registeredUsersFile.toPath(), StandardCharsets.UTF_8);
    assertEquals(exceptedContent, fileContent);
  }

  @Test
  void testMultipleDifferentUsers() throws Exception {
    // Arrange
    final User user1 = new User(new Address("test1", "test.de"), "password", "salt");
    final User user2 = new User(new Address("test2", "test.de"), "password", "salt");

    final String exceptedContent =
        "[{\"email\":{\"localPart\":\"test2\",\"domain\":\"test.de\"},\"password\":\"password\",\"salt\":\"salt\"},{\"email\":{\"localPart\":\"test1\",\"domain\":\"test.de\"},\"password\":\"password\",\"salt\":\"salt\"}]";

    // Act
    fileUserRepository.save(user1);
    fileUserRepository.save(user2);

    // Assert
    final String fileContent =
        Files.readString(registeredUsersFile.toPath(), StandardCharsets.UTF_8);
    assertEquals(exceptedContent, fileContent);
  }

  @Test
  void testFindByEmail() throws IOException, LoadingUsersException, SaveUserException {
    // Arrange
    User user = new User(new Address("test2", "test.de"), "password", "salt");
    Files.writeString(
        registeredUsersFile.toPath(),
        "[{\"email\":{\"localPart\":\"test\",\"domain\":\"test.de\"},\"password\":\"password\",\"salt\":\"salt\"},{\"email\":{\"localPart\":\"test2\",\"domain\":\"test.de\"},\"password\":\"password\",\"salt\":\"salt\"}]");

    // Act
    Optional<User> foundUser = fileUserRepository.findByEmail(user.email());

    // Assert
    assertTrue(foundUser.isPresent());
    assertEquals(foundUser.get(), user);
  }

  @Test
  void testMalformedJSONShouldThrow() throws IOException {
    // Arrange
    User user = new User(new Address("test", "test.de"), "password", "salt");
    Files.writeString(registeredUsersFile.toPath(), "[malformed JSON]");

    // Assert
    assertThrows(
        LoadingUsersException.class,
        // Act
        () -> fileUserRepository.findByEmail(user.email()));
  }
}
