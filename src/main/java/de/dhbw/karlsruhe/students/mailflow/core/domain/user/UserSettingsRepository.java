package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;

public interface UserSettingsRepository {

  void updateUserSettings(UserSettings userSettings)
      throws LoadSettingsException, SaveSettingsException;

  UserSettings getSettings(Address address) throws LoadSettingsException, SaveSettingsException;
}
