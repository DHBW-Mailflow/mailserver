package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;


public class MockedUserSettingsRepository implements UserSettingsRepository {

  private final Address testAddress;

  public MockedUserSettingsRepository(Address testAddress) {
    this.testAddress = testAddress;
  }

  @Override
  public void updateUserSettings(UserSettings userSettings) {}

  @Override
  public UserSettings getSettings(Address address){
    return new UserSettings(testAddress, "Test Signature");
  }
}
