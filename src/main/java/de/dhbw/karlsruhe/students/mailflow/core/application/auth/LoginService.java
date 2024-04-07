package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordAuthenticator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LoadingUsersException;
import java.util.Optional;

/**
 * @author seiferla
 */
public class LoginService implements LoginUseCase {

  private final UserRepository userRepository;

  private final PasswordAuthenticator passwordAuthenticator;

  public LoginService(UserRepository userRepository, PasswordAuthenticator passwordAuthenticator) {
    this.userRepository = userRepository;
    this.passwordAuthenticator = passwordAuthenticator;
  }

  @Override
  public User login(Address email, String password)
      throws AuthorizationException, LoadingUsersException {

    Optional<User> user = userRepository.findByEmail(email);

    if (user.isEmpty()) {
      throw new AuthorizationException("Credentials are incorrect");
    }

    if (!passwordAuthenticator.checkPassword(password, user.get())) {
      throw new AuthorizationException("Credentials are incorrect");
    }

    return user.get();
  }
}
