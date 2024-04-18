package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;

public interface AuthSessionUseCase {

  boolean isLoggedIn();

  /**
   * @return the address of the currently logged-in user
   * @throws IllegalStateException when there is no logged-in user
   */
  Address getSessionUserAddress() throws IllegalStateException;

  /**
   * @throws IllegalStateException when there is no logged-in user
   */
  void ensureLoggedIn();

  void removeSessionUser();

  void setSessionUser(User user);
}
