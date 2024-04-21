package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.RemoveSettingsException;
import java.io.FileNotFoundException;

public interface UserSettingsRepository {

  void updateUserSettings(Address address, UserSettings userSettings) throws LoadSettingsException;

  void removeUserSettings(Address address) throws RemoveSettingsException;

  String getSiginature(Address address) throws LoadSettingsException;
}
