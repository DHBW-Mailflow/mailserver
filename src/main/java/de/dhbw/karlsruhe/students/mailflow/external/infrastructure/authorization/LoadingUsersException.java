package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import java.io.IOException;

/**
 * @author seiferla
 */
public class LoadingUsersException extends Exception {
  public LoadingUsersException(String couldNotLoadUsers, IOException e) {
    super(couldNotLoadUsers, e);
  }
}
