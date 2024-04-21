package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;

/**
 * @author seiferla
 */
public class SignatureBuilder {

  private String signature;

  private Address address;

  public SignatureBuilder withAddress(Address address) {
    this.address = address;
    return this;
  }

  public SignatureBuilder withSignature(String signature) {
    this.signature = signature;
    return this;
  }

  public UserSettings build() {
    return new UserSettings(address, signature);
  }
}
