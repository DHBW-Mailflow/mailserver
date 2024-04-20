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
public class LoginService implements LoginUseCase {
  private final AuthSessionUseCase authSession;
  private final UserRepository userRepository;
  private final PasswordChecker passwordChecker;

  public LoginService(
      AuthSessionUseCase authSession,
      UserRepository userRepository,
      PasswordChecker passwordChecker) {
    this.authSession = authSession;
    this.userRepository = userRepository;
    this.passwordChecker = passwordChecker;
  }

  @Override
  public void login(Address email, String password)
      throws AuthorizationException, LoadingUsersException {
    try {
      authorizeUser(email, password);
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

    authSession.setSessionUser(user);
  }
}
