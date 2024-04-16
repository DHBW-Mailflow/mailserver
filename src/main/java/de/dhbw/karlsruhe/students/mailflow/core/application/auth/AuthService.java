package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import java.util.Optional;

/**
 * @author seiferla
 */
public class AuthService implements AuthUseCase {

  private final UserRepository userRepository;

  private final PasswordChecker passwordChecker;
  private User sessionUser;

  public AuthService(UserRepository userRepository, PasswordChecker passwordChecker) {
    this.userRepository = userRepository;
    this.passwordChecker = passwordChecker;
  }

  @Override
  public void login(String email, String password)
      throws AuthorizationException, LoadingUsersException {
    try {
      Address address = Address.from(email);
      authorizeUser(address, password);
    } catch (IllegalArgumentException e) {
      throw new AuthorizationException("Credentials are incorrect. " + e.getMessage());
    }
  }

  private void authorizeUser(Address address, String password)
      throws LoadingUsersException, AuthorizationException {
    Optional<User> foundUser = userRepository.findByEmail(address);

    if (foundUser.isEmpty()) {
      throw new AuthorizationException("Credentials are incorrect");
    }

    var user = foundUser.get();

    if (!passwordChecker.checkPassword(password, user)) {
      throw new AuthorizationException("Credentials are incorrect");
    }

    this.sessionUser = user;
  }

  @Override
  public Address logout() {
    ensureLoggedIn();
    var userCopy = sessionUser;
    this.sessionUser = null;
    return userCopy.email();
  }

  @Override
  public boolean isLoggedIn() {
    return sessionUser != null;
  }

  @Override
  public Address getSessionUserAddress() {
    ensureLoggedIn();
    return sessionUser.email();
  }

  @Override
  public void ensureLoggedIn() {
    if (sessionUser == null) {
      throw new IllegalStateException("No user is logged in");
    }
  }
}
