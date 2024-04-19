package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public interface LogoutUseCase {
  /**
   * @return the address of the user that was logged out
   * @throws IllegalStateException when there is no logged-in user
   */
  Address logout() throws IllegalStateException;
}
