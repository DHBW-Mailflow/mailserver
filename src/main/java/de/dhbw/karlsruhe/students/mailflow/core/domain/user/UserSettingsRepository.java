package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.RemoveSettingsException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.SaveSettingsException;

public interface UserSettingsRepository {

  boolean updateUserSettings(UserSettings userSettings) throws LoadSettingsException, SaveSettingsException;

  void removeUserSettings(Address address) throws LoadSettingsException, SaveSettingsException;

  UserSettings getSettings(Address address) throws LoadSettingsException, SaveSettingsException;
}
