package de.dhbw.karlsruhe.students.mailflow.core.domain.auth;

/**
 * @author seiferla
 */
public class HashingFailedException extends Exception {
  public HashingFailedException(String errorMessage, Throwable cause) {
    super(errorMessage, cause);
  }
}
