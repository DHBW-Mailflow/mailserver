package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;

public class FileUserSettingsRepository implements UserSettingsRepository {

  @Override
  public UserSettings getUserSettings(User user) {
    return null;
  }

  @Override
  public void updateUserSettings(User user, UserSettings userSettings) {

  }

  @Override
  public void removeUserSettings(User user) {

  }

  @Override
  public void createUserSettings(User user, UserSettings userSettings) {

  }

  @Override
  public void saveUserSettings(UserSettings userSettings) {

  }
}
