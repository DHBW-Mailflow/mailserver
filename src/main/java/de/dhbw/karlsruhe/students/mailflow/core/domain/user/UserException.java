package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class UserException extends IllegalArgumentException {
  public UserException(String errorMessage) {
    super(errorMessage);
  }
}
