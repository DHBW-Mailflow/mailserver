package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

/**
 * @author seiferla
 */
public class LoadingUsersException extends Exception {
  public LoadingUsersException(String couldNotLoadUsers, Exception e) {
    super(couldNotLoadUsers, e);
  }
}
