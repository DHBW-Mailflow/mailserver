package de.dhbw.karlsruhe.students.mailflow.core.application.auth;


public interface LogoutUseCase {
  /**
   * @return the address of the user that was logged out
   * @throws IllegalStateException when there is no logged-in user
   */
  String logout() throws IllegalStateException;
}
