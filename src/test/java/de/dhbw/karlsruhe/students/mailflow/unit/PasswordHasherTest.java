package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.PasswordHasher;
import org.junit.jupiter.api.Test;

/**
 * @author Jonas-Karl
 */
class PasswordHasherTest {

  @Test
  void testAlgorithmIsSHA256() throws HashingFailedException {
    // Arrange
    String expectedSHA256Hash = "2f89b50dbdb72487c468518cfc411c2b6adf0645d59b99023a239640ccc561dc";

    // Act
    String hashedPassword = PasswordHasher.hashPassword("password", "exampleSalt");

    // Assert
    assertEquals(expectedSHA256Hash, hashedPassword);
  }
}
