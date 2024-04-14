package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public abstract class AbstractCLIPrompt {

    public String simplePrompt(String question) {
        System.out.println(question);
        return readUserInput();
    }

  private String readUserInput() {
    return new Scanner(System.in).nextLine();
  }

  private int numberCalled;

  public AbstractCLIPrompt readUserInputWithOptions(Map<String, AbstractCLIPrompt> options) {
    List<Map.Entry<String, AbstractCLIPrompt>> entries = options.entrySet().stream().toList();
    showOptions(entries);
    String input = readUserInput();
    return retryWithInvalidSelection(options, entries, input);
  }

  private AbstractCLIPrompt retryWithInvalidSelection(
      Map<String, AbstractCLIPrompt> options,
      List<Entry<String, AbstractCLIPrompt>> entries,
      String input) {
    try {
      int parsedInput = Integer.parseInt(input);
      numberCalled = 0;
      return entries.get(parsedInput).getValue();
    } catch (NumberFormatException e) {
      if (numberCalled > 3) {
        System.err.println("Too many tries");
        System.exit(0);
      }
      System.err.println("Your input was not a number. Please try again");
      return readUserInputWithOptions(options);
    }
  }

  private void showOptions(List<Entry<String, AbstractCLIPrompt>> entries) {
    for (int i = 0; i < entries.size(); i++) {
      System.out.printf("[%s]: %s", i, entries.get(i).getKey());
      System.out.println();
    }
  }

  public abstract void start();
}
