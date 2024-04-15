package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BaseCLIPromptTest {
  private BaseCLIPrompt baseCLIPrompt;
  private static final InputStream originalStandardIn = System.in;

  @AfterEach
  void tearDown() {
    System.setIn(originalStandardIn);
  }

  @Test
  void readUserInputWithOptions() {
    // Arrange
    System.setIn(new ByteArrayInputStream("1\n".getBytes()));

    baseCLIPrompt = new BaseCLIPrompt();
    baseCLIPrompt.start();

    // Act
    Map<String, BaseCLIPrompt> options = new HashMap<>();
    options.put("First option", new BaseCLIPrompt());
    options.put("Second option", new BaseCLIPrompt());
    baseCLIPrompt.readUserInputWithOptions(options);

    // Assert
    assertTrue(true);
  }
}
