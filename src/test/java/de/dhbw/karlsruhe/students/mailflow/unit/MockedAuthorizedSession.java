package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;

final class MockedAuthorizedSession implements AuthSessionUseCase {

  private Address address;

  public MockedAuthorizedSession(Address address) {
    this.address = address;
  }

  @Override
  public boolean isLoggedIn() {
    return address != null;
  }

  @Override
  public Address getSessionUserAddress() throws IllegalStateException {
    return address;
  }

  @Override
  public void ensureLoggedIn() {
    // do not throw
  }

  @Override
  public void removeSessionUser() {
    address = null;
  }

  @Override
  public void setSessionUser(User user) {
    address = user.email();
  }
}
