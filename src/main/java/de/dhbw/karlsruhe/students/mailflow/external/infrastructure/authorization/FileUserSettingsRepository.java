package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUserSettingsRepository implements UserSettingsRepository {

  private static final File USERS_SETTINGS_FILE = new File("users.json");

  @Override
  public void updateUserSettings(Address address, UserSettings userSettings)
      throws LoadSettingsException {
    try {
      StringBuilder fileContent = readFileContent();
      updateFileContent(fileContent, address, userSettings);
      writeFileContent(fileContent);
    } catch (FileNotFoundException e) {
      throw new LoadSettingsException("Failed to update user settings", e);
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
      StringBuilder fileContent, Address address, UserSettings userSettings)
      throws FileNotFoundException {

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
  public void removeUserSettings(Address address) {
    try (Scanner scanner = new Scanner(USERS_SETTINGS_FILE)) {
      StringBuilder fileContent = new StringBuilder();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (!line.contains(address.toString())) {
          fileContent.append(line).append("\n");
        }
      }
      try (PrintWriter writer = new PrintWriter(USERS_SETTINGS_FILE)) {
        writer.write(fileContent.toString());
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Failed to remove user settings", e);
    }
  }
}
