package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * @author Jonas-Karl
 */
public class LogoutService implements LogoutUseCase {
  private final AuthSessionUseCase authSession;

  public LogoutService(AuthSessionUseCase authSession) {
    this.authSession = authSession;
  }

  @Override
  public String logout() {
    Address loggedOutUser = authSession.getSessionUserAddress();
    authSession.removeSessionUser();
    return loggedOutUser.toString();
  }
}
