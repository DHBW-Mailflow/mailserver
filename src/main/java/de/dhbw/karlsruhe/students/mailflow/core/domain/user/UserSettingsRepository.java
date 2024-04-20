package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public interface UserSettingsRepository {

  UserSettings getUserSettings(User user);

  void updateUserSettings(User user, UserSettings userSettings);

  void removeUserSettings(User user);

  void createUserSettings(User user, UserSettings userSettings);

  void saveUserSettings(UserSettings userSettings);
}
