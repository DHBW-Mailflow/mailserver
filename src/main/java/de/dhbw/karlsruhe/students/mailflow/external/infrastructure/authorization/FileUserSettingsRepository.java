package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class FileUserSettingsRepository implements UserSettingsRepository {

  private static final File USERS_SETTINGS_FILE = new File("settings.json");

  private final Gson gson;

  private final Set<UserSettings> usersSettings;

  public FileUserSettingsRepository() {
    this.gson = new Gson();
    this.usersSettings = new HashSet<>();
  }

  private void readUserSettings(Address address) throws LoadSettingsException, SaveSettingsException {
    usersSettings.clear();
    createFileIfNotExists(address);
    try (FileReader reader = new FileReader(USERS_SETTINGS_FILE)) {
      Type setType = new TypeToken<HashSet<UserSettings>>() {}.getType();
      HashSet<UserSettings> parsedUsers = gson.fromJson(reader, setType);
      if (parsedUsers == null) {
        return;
      }
      usersSettings.addAll(parsedUsers);
    } catch (IOException e) {
      throw new LoadSettingsException("Could not load user settings");
    }
  }

  private void createFileIfNotExists(Address address) throws LoadSettingsException, SaveSettingsException {
    if (USERS_SETTINGS_FILE.exists()) {
      return;
    }
    try {
      if (!USERS_SETTINGS_FILE.createNewFile()) {
        throw new LoadSettingsException("Could not find usersettings file");
      }
      Set<UserSettings> userSettings = Set.of(new UserSettings(address, ""));
      try (FileWriter writer = new FileWriter(USERS_SETTINGS_FILE)) {
        gson.toJson(userSettings, writer);
      }
    } catch (IOException | SecurityException e) {
      throw new SaveSettingsException("Could not create usersettings file", e);
    }
  }

  @Override
  public void updateUserSettings(UserSettings userSettings)
      throws LoadSettingsException, SaveSettingsException {
    readUserSettings(userSettings.address());
    usersSettings.removeIf(settings -> settings.address().equals(userSettings.address()));
    usersSettings.add(userSettings);
    save();
  }

  private boolean save() throws SaveSettingsException {
    try (FileWriter writer = new FileWriter(USERS_SETTINGS_FILE)) {
      gson.toJson(this.usersSettings, writer);
      return true;
    } catch (IOException e) {
      throw new SaveSettingsException("Could not save user settings", e);
    }
  }

  @Override
  public void removeUserSettings(Address address)
      throws LoadSettingsException, SaveSettingsException {
    updateUserSettings(new UserSettings(address, ""));
  }

  @Override
  public UserSettings getSettings(Address address)
      throws LoadSettingsException, SaveSettingsException {
    readUserSettings(address);
    return usersSettings.stream()
        .filter(userSettings -> userSettings.address().equals(address))
        .findFirst()
        .orElseThrow(() -> new LoadSettingsException("Could not find user settings"));
  }
}
