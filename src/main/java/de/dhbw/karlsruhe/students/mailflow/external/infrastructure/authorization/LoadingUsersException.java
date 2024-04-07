package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

/**
 * @author seiferla
 */
public class LoadingUsersException extends Exception {
  public LoadingUsersException(String message, Exception e) {
    super(message, e);
  }

  public LoadingUsersException(String message) {
    super(message);
  }
}
