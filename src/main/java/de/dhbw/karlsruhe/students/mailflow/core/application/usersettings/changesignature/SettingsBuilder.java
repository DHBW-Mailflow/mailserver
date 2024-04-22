package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;

/**
 * @author seiferla
 */
public class SettingsBuilder {

  private String signature;

  private Address address;

  public SettingsBuilder(UserSettings currentUserSettings) {
    this.address = currentUserSettings.address();
    this.signature = currentUserSettings.signature();
  }

  public SettingsBuilder() {}

  public SettingsBuilder withAddress(Address address) {
    this.address = address;
    return this;
  }

  public SettingsBuilder withSignature(String signature) {
    this.signature = signature;
    return this;
  }

  public UserSettings build() {
    return new UserSettings(address, signature);
  }
}
