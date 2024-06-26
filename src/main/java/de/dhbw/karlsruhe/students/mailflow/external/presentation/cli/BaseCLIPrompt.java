package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.job.WorkerQueue;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import java.util.LinkedHashMap;
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
public abstract class BaseCLIPrompt implements Server {
  private static final int MAX_ATTEMPTS = 3;
  private final BaseCLIPrompt previousPrompt;
  private int attemptCount;
  private Scanner scanner;

  protected BaseCLIPrompt(BaseCLIPrompt previousPrompt) {
    this.previousPrompt = previousPrompt;
  }

  /** Starts the server or CLIPrompt */
  @Override
  public void start() {
    WorkerQueue.getInstance().performDueJobs();

    scanner = new Scanner(System.in);
  }

  public BaseCLIPrompt getPreviousPrompt() {
    if (previousPrompt == null) {
      throw new IllegalStateException("No previous prompt available");
    }
    previousPrompt.resetAttemptCounter();
    return previousPrompt;
  }

  private void resetAttemptCounter() {
    attemptCount = 0;
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
   * @return the (trimmed) client answer input
   */
  public String simplePrompt(String question) {
    printDefault(question);
    return readUserInput().trim();
  }

  /**
   * Provides a list of options to the client. The client has 3 attempts to select a valid option.
   *
   * @param options for each option entry, there is a new CLI-Prompt
   * @return the new CLI-Prompt after the user selected an option
   */
  public BaseCLIPrompt readUserInputWithOptions(Map<String, BaseCLIPrompt> options) {
    if (previousPrompt != null) {
      if (options instanceof LinkedHashMap<String, BaseCLIPrompt> optionsMap) {
        optionsMap.putFirst("Go back", previousPrompt);
      } else {
        options.put("Go back", previousPrompt);
      }
    }
    showOptions(options);
    String input = readUserInput();
    return retryOnInvalidSelection(options, input);
  }

  /**
   * @return the (untrimmed) string consisting of the user input
   */
  private String readUserInput() {
    try {
      return scanner.nextLine();
    } catch (NoSuchElementException ignored) {
      printWarning("Detected shutdown shortcut. Exiting...");
      stop();
      return "";
    }
  }

  /**
   * Reads the user input until a Ctrl + D or :q on an empty line is received
   *
   * @return the untrimmed answer from the user consisting of several lines
   */
  public String readMultilineUserInput() {
    StringBuilder stringBuilder = new StringBuilder();

    while (scanner.hasNextLine()) {
      String line = readUserInput();
      if (line.trim().equals(":q")) {
        break;
      }
      stringBuilder.append(line).append("\n");
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
      stop();
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

  public String formatEmailListing(Email email) {
    return "%s: %s - %s"
        .formatted(
            email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  public String formatEmailContent(Email email) {
    if (email == null) {
      return "";
    }

    String recipientsTo = formatAddressList(email.getRecipientTo());
    String recipientsCC = formatAddressList(email.getRecipientCC());
    String recipientsBCC = formatAddressList(email.getRecipientBCC());
    String previousMails = formatEmailContent(email.getPreviousMail());

    return """
        Sender: %s
        To: %s
        CC: %s
        BCC: %s
        Date: %s

        %s
        ---------------
        %s
        """
        .formatted(
            email.getSender(),
            recipientsTo,
            recipientsCC,
            recipientsBCC,
            email.getSendDate().formattedDate(),
            email.getContent(),
            previousMails);
  }

  private String formatAddressList(List<Address> addresses) {
    if (addresses == null || addresses.isEmpty()) {
      return "-";
    }

    StringBuilder builder = new StringBuilder();
    for (Address address : addresses) {
      builder.append(address.toString()).append(",");
    }
    builder.deleteCharAt(builder.length() - 1); // remove trailing ","
    return builder.toString();
  }
}
