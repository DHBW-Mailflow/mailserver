package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.utils.FileHelper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @author seiferla
 */
public class FileUserSettingsRepository implements UserSettingsRepository {

  private static final File USERS_SETTINGS_FILE = new File("settings.json");

  private final Gson gson;

  private final Set<UserSettings> usersSettings;

  private final FileHelper fileHelper;
  private String defaultFileContent;
  private final Type setType = new TypeToken<HashSet<UserSettings>>() {}.getType();

  public FileUserSettingsRepository() {
    this.gson = new Gson();
    this.usersSettings = new HashSet<>();
    fileHelper = new FileHelper();
  }

  private void initFile(Address address) throws IOException {

    if (fileHelper.existsFile(USERS_SETTINGS_FILE)) {
      return;
    }
    Set<UserSettings> userSettings = Set.of(new UserSettings(address, ""));
    defaultFileContent = gson.toJson(userSettings);
    fileHelper.saveToFile(USERS_SETTINGS_FILE, defaultFileContent);
  }

  private void readUserSettings(Address address)
      throws LoadSettingsException, SaveSettingsException {
    usersSettings.clear();
    try {
      initFile(address);
    } catch (IOException e) {
      throw new SaveSettingsException("Could not initialize File", e);
    }
    try {
      String content = fileHelper.readFileContent(USERS_SETTINGS_FILE, defaultFileContent);
      HashSet<UserSettings> parsedUsers = gson.fromJson(content, setType);
      if (parsedUsers == null) {
        return;
      }
      usersSettings.addAll(parsedUsers);
    } catch (IOException e) {
      throw new LoadSettingsException("Could not parse user settings");
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

  private void save() throws SaveSettingsException {
    try {
      fileHelper.saveToFile(USERS_SETTINGS_FILE, gson.toJson(this.usersSettings));
    } catch (IOException e) {
      throw new SaveSettingsException("Could not save user settings", e);
    }
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
