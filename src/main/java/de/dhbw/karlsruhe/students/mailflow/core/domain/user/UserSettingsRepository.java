package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.FileNotFoundException;

public interface UserSettingsRepository {

  void updateUserSettings(Address address, UserSettings userSettings) throws FileNotFoundException;

  void removeUserSettings(Address address);
}
