package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.FileUserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LoadingUsersException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileUserRepositoryTest {

  /**
   * @author seiferla
   */
  @Test
  void testSaveUser(@TempDir File tempDir)
      throws SaveUserException, LoadingUsersException, IOException {

    // Arrange

    User user = new User(new Address("test", "test.de"), "password", "salt");

    final File file = new File(tempDir, "registered-users.json");
    FileUserRepository fileUserRepository = new FileUserRepository(file);
    file.createNewFile();

    // Act
    fileUserRepository.save(user);
    String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
    String exceptedContent =
        "[{\"email\":{\"localPart\":\"test\",\"domain\":\"test.de\"},\"password\":\"password\",\"salt\":\"salt\"}]";
    // Assert
    assertEquals(exceptedContent, fileContent);
  }

  @Test
  void testFindByEmail(@TempDir File tempDir)
      throws HashingFailedException, SaveUserException, IOException, LoadingUsersException {
    // Arrange
    User user = new User(new Address("test", "test.de"), "password", "salt");

    final File file = new File(tempDir, "registered-users.json");
    file.createNewFile();
    Files.writeString(file.toPath(), "... content ");

    // Act
    FileUserRepository fileUserRepository = new FileUserRepository(file);
    Optional<User> foundUser = fileUserRepository.findByEmail(user.email());

    // Assert
    assertTrue(foundUser.isPresent());
  }
}
