package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseCLIPromptTest {
  private BaseCLIPrompt baseCLIPrompt;

  @BeforeEach
  void setUp() {
    baseCLIPrompt = new BaseCLIPrompt();
    baseCLIPrompt.start();
  }

  @AfterEach
  void tearDown() {
    baseCLIPrompt.stop();
    System.setIn(System.in);
  }

  @Test
  void readUserInputWithOptions() {
    System.setIn(new ByteArrayInputStream("1".getBytes()));
    Map<String, BaseCLIPrompt> options = new HashMap<>();
    options.put("First option", new BaseCLIPrompt());
    options.put("Second option", new BaseCLIPrompt());
    baseCLIPrompt.readUserInputWithOptions(options);
  }
}
