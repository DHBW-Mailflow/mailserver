package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BaseCLIPromptTest {
  private static final InputStream originalStandardIn = System.in;

  @AfterEach
  void tearDown() {
    System.setIn(originalStandardIn);
  }

  @Test
  void readUserInputWithOptions() throws MailboxSavingException, MailboxLoadingException {
    // Arrange
    System.setIn(new ByteArrayInputStream("1\n".getBytes()));
    final BaseCLIPrompt baseCLIPrompt = new BaseCLIPrompt();
    baseCLIPrompt.start();

    // Act
    var expectedResult = new BaseCLIPrompt();

    final Map<String, BaseCLIPrompt> options = new HashMap<>();
    options.put("First option", new BaseCLIPrompt());
    options.put("Second option", expectedResult);
    final var resultPrompt = baseCLIPrompt.readUserInputWithOptions(options);

    // Assert
    assertEquals(expectedResult, resultPrompt);
  }
}
