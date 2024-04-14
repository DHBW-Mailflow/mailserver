package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.InvalidEmailException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.InvalidPasswordException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.InvalidSaltException;

/**
 * @author seiferla
 */
public record User(Address email, String password, String salt) {

  public User {
    if (email == null || email.toString().isBlank()) {
      throw new InvalidEmailException("Email must not be null or empty");
    }
    if (password == null || password.isBlank()) {
      throw new InvalidPasswordException("Password must not be null or empty");
    }
    if (salt == null || salt.isBlank()) {
      throw new InvalidSaltException("Salt must not be null or empty");
    }
  }

  public boolean hasEmail(Address email) {
    return this.email.equals(email);
  }
}
