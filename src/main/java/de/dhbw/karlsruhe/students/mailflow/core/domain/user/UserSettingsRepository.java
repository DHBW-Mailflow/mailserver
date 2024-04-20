package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import java.io.FileNotFoundException;

public interface UserSettingsRepository {

  UserSettings getUserSettings(User user);

  void updateUserSettings(User user, UserSettings userSettings) throws FileNotFoundException;

  void removeUserSettings(User user);

  void createUserSettings(User user, UserSettings userSettings);

}
