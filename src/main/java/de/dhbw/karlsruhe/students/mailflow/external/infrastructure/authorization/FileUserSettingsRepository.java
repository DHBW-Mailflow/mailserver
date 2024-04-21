package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.function.Function;

public class FileUserSettingsRepository implements UserSettingsRepository {

  private static final File USERS_SETTINGS_FILE = new File("users.json");

  @Override
  public void updateUserSettings(Address address, UserSettings userSettings)
      throws LoadSettingsException {
    try {
      processUserSettings(
          fileContent -> {
            updateFileContent(fileContent, address, userSettings);
            return null;
          });
    } catch (FileNotFoundException e) {
      throw new LoadSettingsException("Failed to update user settings");
    }
  }

  private StringBuilder readFileContent() throws FileNotFoundException {
    StringBuilder fileContent = new StringBuilder();
    try (Scanner scanner = new Scanner(USERS_SETTINGS_FILE)) {
      while (scanner.hasNextLine()) {
        fileContent.append(scanner.nextLine()).append("\n");
      }
    }
    return fileContent;
  }

  private void updateFileContent(
      StringBuilder fileContent, Address address, UserSettings userSettings) {
    String fileContentString = fileContent.toString();
    String updatedLine = address + ": " + userSettings.toString() + "\n";
    String oldLine = address + ": .*" + "\n";
    String updatedFileContent = fileContentString.replaceAll(oldLine, updatedLine);
    fileContent.replace(0, fileContent.length(), updatedFileContent);
  }

  private void writeFileContent(StringBuilder fileContent) throws FileNotFoundException {
    try (PrintWriter writer = new PrintWriter(USERS_SETTINGS_FILE)) {
      writer.write(fileContent.toString());
    }
  }

  @Override
  public void removeUserSettings(Address address) throws RemoveSettingsException {
    try {
      processUserSettings(
          fileContent -> {
            removeUserSettingsFromFileContent(fileContent, address);
            return null;
          });
    } catch (FileNotFoundException e) {
      throw new RemoveSettingsException("Failed to remove user settings", e);
    }
  }

  @Override
  public String getSiginature(Address address) throws LoadSettingsException {
    try {
      StringBuilder fileContent = readFileContent();
      String[] lines = fileContent.toString().split("\n");
      for (String line : lines) {
        if (line.startsWith(address.toString())) {
          return line.substring(line.indexOf(":") + 1).trim();
        }
      }
      throw new LoadSettingsException("No settings found for the given address");
    } catch (FileNotFoundException e) {
      throw new LoadSettingsException("Failed to load user settings");
    }
  }

  private void processUserSettings(Function<StringBuilder, Void> fileContentProcessor)
      throws FileNotFoundException {
    StringBuilder fileContent = readFileContent();
    fileContentProcessor.apply(fileContent);
    writeFileContent(fileContent);
  }

  private void removeUserSettingsFromFileContent(StringBuilder fileContent, Address address) {
    String fileContentString = fileContent.toString();
    String oldLinePattern = address + ":.*\n";
    String updatedFileContent = fileContentString.replaceAll(oldLinePattern, "");
    fileContent.replace(0, fileContent.length(), updatedFileContent);
  }
}
