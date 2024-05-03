package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import java.util.Objects;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.InvalidEmailException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.InvalidPasswordException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.InvalidSaltException;

/**
 * @author seiferla
 */
public final class User {

  private Address address;
  private String password;
  private String salt;

  public User(Address address, String password, String salt) {
    validateAddress(address);
    validateSalt(salt);
    validatePassword(password);

    this.address = address;
    this.password = password;
    this.salt = salt;
  }

  private void validateAddress(Address address) {
    if (address == null || address.toString().isBlank()) {
      throw new InvalidEmailException("Address must not be null or empty");
    }
  }

  private void validateSalt(String salt) {
    if (salt == null || salt.isBlank()) {
      throw new InvalidSaltException("Salt must not be null or empty");
    }
  }

  private void validatePassword(String password) {
    if (password == null || password.isBlank()) {
      throw new InvalidPasswordException("Password must not be null or empty");
    }
  }

  public boolean hasAddress(Address email) {
    return this.address.equals(email);
  }

  public void setAddress(Address address) {
    validateAddress(address);
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }

  public void setPassword(String password) {
    validatePassword(password);
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setSalt(String salt) {
    validateSalt(salt);
    this.salt = salt;
  }

  public String getSalt() {
    return salt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(address);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof User))
      return false;
    User other = (User) obj;
    return Objects.equals(address, other.address);
  }
}
