package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;

/**
 * @author Jonas-Karl
 */
public class AuthSession implements AuthSessionUseCase {
  private User sessionUser;

  @Override
  public boolean isLoggedIn() {
    return sessionUser != null;
  }

  @Override
  public Address getSessionUserAddress() {
    ensureLoggedIn();
    return sessionUser.getAddress();
  }

  @Override
  public void ensureLoggedIn() {
    if (sessionUser == null) {
      throw new IllegalStateException("No user is logged in");
    }
  }

  /** used by LogoutService */
  @Override
  public void removeSessionUser() {
    sessionUser = null;
  }

  /** used by LoginService */
  @Override
  public void setSessionUser(User user) {
    sessionUser = user;
  }
}
