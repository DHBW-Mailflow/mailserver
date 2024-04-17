package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public interface AuthUseCase {

  void login(String email, String password) throws AuthorizationException, LoadingUsersException;

  /**
   * @return the address of the user that was logged out
   * @throws IllegalStateException when there is no logged-in user
   */
  Address logout() throws IllegalStateException;

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
}
