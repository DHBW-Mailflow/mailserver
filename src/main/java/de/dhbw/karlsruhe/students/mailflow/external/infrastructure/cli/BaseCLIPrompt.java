package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * abstract representation of an interactive CLI-Prompt. It provides #simplePrompt and
 * #readUserInputWithOptions
 *
 * @author Jonas-Karl, jens1o
 */
public class BaseCLIPrompt implements Server {

  private static final int MAX_ATTEMPTS = 3;
  private int attemptCount;
  private Scanner scanner;

  /** Starts the server or CLIPrompt */
  @Override
  public void start() {
    scanner = new Scanner(System.in);
  }

  /** Stops the server */
  @Override
  public void stop() {
    if (scanner != null) {
      scanner.close();
    }
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

  /**
   * Provides a list of options to the client. The client has 3 attempts to select a valid option.
   *
   * @param options for each option entry, there is a new CLI-Prompt
   * @return the new CLI-Prompt after the user selected an option
   */
  public BaseCLIPrompt readUserInputWithOptions(Map<String, BaseCLIPrompt> options) {
    showOptions(options);
    String input = readUserInput();
    return retryOnInvalidSelection(options, input);
  }

  private String readUserInput() {
    return scanner.nextLine();
  }

  /**
   * Reads the user input until a Ctrl + D (Linux/macOS) or Ctrl + Z (Windows) is received
   *
   * @return the untrimmed answer from the user consisting of several lines
   */
  public String readMultilineUserInput() {
    StringBuilder stringBuilder = new StringBuilder();

    while (scanner.hasNextLine()) {
      try {
        stringBuilder.append(scanner.nextLine()).append("\n");
      } catch (NoSuchElementException e) {
        break;
      }
    }

    return stringBuilder.toString();
  }

  /**
   * Tries to parse the client input. If the input is not a valid selection, the client can try 2
   * more times. After that, the server stops.
   *
   * @param options available options
   * @param input selected user input
   * @return the interactive CLI-Prompt for the selected option
   */
  private BaseCLIPrompt retryOnInvalidSelection(Map<String, BaseCLIPrompt> options, String input) {
    List<Map.Entry<String, BaseCLIPrompt>> entries = options.entrySet().stream().toList();
    try {
      int parsedInput = Integer.parseInt(input);
      return entries.get(parsedInput).getValue();
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
      attemptCount++;
      return retry(options);
    }
  }

  private BaseCLIPrompt retry(Map<String, BaseCLIPrompt> options) {
    if (attemptCount > MAX_ATTEMPTS) {
      printWarning("Too many attempts!");
      System.exit(0);
    }
    printWarning("Your input was not a valid number. Please try again");
    return readUserInputWithOptions(options);
  }

  /**
   * Prints every option and their index
   *
   * @param options every available option for the client to select
   */
  private void showOptions(Map<String, BaseCLIPrompt> options) {
    List<Map.Entry<String, BaseCLIPrompt>> entries = options.entrySet().stream().toList();

    for (int i = 0; i < entries.size(); i++) {
      printDefault("[%s]: %s".formatted(i, entries.get(i).getKey()));
    }
  }
}
