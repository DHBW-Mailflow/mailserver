package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;

public class MockedUserSettingsRepository implements UserSettingsRepository {

  private final Address testAddress = new Address("test", "example.com");

  @Override
  public void updateUserSettings(UserSettings userSettings)
      throws LoadSettingsException, SaveSettingsException {}

  @Override
  public UserSettings getSettings(Address address)
      throws LoadSettingsException, SaveSettingsException {
    return new UserSettings(testAddress, "Test Signature");
  }
}
