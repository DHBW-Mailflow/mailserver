package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

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
  public void updateUserSettings(Address address, UserSettings userSettings) {
    try (Scanner scanner = new Scanner(USERS_SETTINGS_FILE)) {
      StringBuilder fileContent = new StringBuilder();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.contains(address.toString())) {
          line = address + ": " + userSettings.toString();
        }
        fileContent.append(line).append("\n");
      }
      try (PrintWriter writer = new PrintWriter(USERS_SETTINGS_FILE)) {
        writer.write(fileContent.toString());
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Failed to update users.json", e);
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
