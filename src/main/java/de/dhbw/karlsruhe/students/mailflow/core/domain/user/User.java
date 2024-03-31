package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;


public record User(Address email, String password) {


  public User {
    if (email == null || email.toString().isBlank()) {
      throw new InvalidEmailException("Email must not be null or empty");
    }
    if (password == null || password.isBlank()) {
      throw new InvalidPasswordException("Password must not be null or empty");
    }
  }

}
