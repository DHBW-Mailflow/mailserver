package de.dhbw.karlsruhe.students.mailflow.core.domain.auth;

/**
 * @author seiferla
 */
public class AuthorizationException extends Exception {

  public AuthorizationException(String errorMessage) {
    super(errorMessage);
  }

  public AuthorizationException(String errorMessage, Throwable cause) {
    super(errorMessage, cause);
  }
}
