package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public record UserSettings(Address address, String signature) {

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

}
