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

  private final UserRepository userRepository;

  private final PasswordChecker passwordChecker;

  public LoginService(UserRepository userRepository, PasswordChecker passwordChecker) {
    this.userRepository = userRepository;
    this.passwordChecker = passwordChecker;
  }

  @Override
  public User login(Address email, String password)
      throws AuthorizationException, LoadingUsersException {

    Optional<User> foundUser = userRepository.findByEmail(email);

    if (foundUser.isEmpty()) {
      throw new AuthorizationException("Credentials are incorrect");
    }

    var user = foundUser.get();

    if (!passwordChecker.checkPassword(password, user)) {
      throw new AuthorizationException("Credentials are incorrect");
    }

    return user;
  }
}
