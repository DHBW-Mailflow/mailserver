package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * abstract representation of an interactive CLI-Prompt. It provides #simplePrompt and
 * #readUserInputWithOptions
 *
 * @author Jonas-Karl
 */
public abstract class AbstractCLIPrompt implements Server {

  private static final int MAX_ATTEMPTS = 3;
  private int attemptCount;

  /** Stops the server */
  @Override
  public void stop() {
    System.exit(0);
  }

  public void printDefault(String text) {
    System.out.println(text);
  }

  public void printWarning(String text) {
    System.err.println(text);
  }

  /**
   * @param question the question to display
   * @return the client answer input
   */
  public String simplePrompt(String question) {
    printDefault(question);
    return readUserInput();
  }

  private String readUserInput() {
    return new Scanner(System.in).nextLine();
  }

  /**
   * Provides a list of options to the client. The client has 3 attempts to select a valid option.
   *
   * @param options for each option entry, there is a new CLI-Prompt
   * @return the new CLI-Prompt after the user selected an option
   */
  public AbstractCLIPrompt readUserInputWithOptions(Map<String, AbstractCLIPrompt> options) {
    showOptions(options);
    String input = readUserInput();
    return retryOnInvalidSelection(options, input);
  }

  /**
   * Tries to parse the client input. If the input is not a valid selection, the client can try 2
   * more times. After that, the server stops.
   *
   * @param options available options
   * @param input selected user input
   * @return the interaktive CLI-Prompt for the selected option
   */
  private AbstractCLIPrompt retryOnInvalidSelection(
      Map<String, AbstractCLIPrompt> options, String input) {
    List<Map.Entry<String, AbstractCLIPrompt>> entries = options.entrySet().stream().toList();
    try {
      int parsedInput = Integer.parseInt(input);
      attemptCount = 0;
      return entries.get(parsedInput).getValue();
    } catch (NumberFormatException e) {
      return retry(options);
    }
  }

  private AbstractCLIPrompt retry(Map<String, AbstractCLIPrompt> options) {
    if (attemptCount > MAX_ATTEMPTS) {
      printWarning("Too many attempts!");
      System.exit(0);
    }
    printWarning("Your input was not a number. Please try again");
    return readUserInputWithOptions(options);
  }

  /**
   * Prints every option and their index
   *
   * @param options every available option for the client to select
   */
  private void showOptions(Map<String, AbstractCLIPrompt> options) {
    List<Map.Entry<String, AbstractCLIPrompt>> entries = options.entrySet().stream().toList();

    for (int i = 0; i < entries.size(); i++) {
      printDefault("[%s]: %s".formatted(i, entries.get(i).getKey()));
    }
  }
}
