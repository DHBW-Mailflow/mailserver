package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.Objects;

/**
 * @author seiferla
 */
public final class UserSettings {
  private final Address address;
  private final String signature;

  /** */
  public UserSettings(Address address, String signature) {
    this.address = address;
    this.signature = signature;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    UserSettings that = (UserSettings) obj;
    return address.equals(that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address);
  }

  public Address address() {
    return address;
  }

  public String signature() {
    return signature;
  }

  @Override
  public String toString() {
    return "UserSettings[" + "address=" + address + ", " + "signature=" + signature + ']';
  }
}
