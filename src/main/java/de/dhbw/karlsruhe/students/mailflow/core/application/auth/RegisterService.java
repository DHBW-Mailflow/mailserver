package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserAuthenticator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LoadingUsersException;
import java.util.Optional;

/**
 * @author seiferla
 */
public class RegisterService implements RegisterUseCase {

  private final UserRepository userRepository;

  private final UserAuthenticator userAuthenticator;

  public RegisterService(UserRepository userRepository, UserAuthenticator userAuthenticator) {
    this.userRepository = userRepository;
    this.userAuthenticator = userAuthenticator;
  }

  @Override
  public boolean register(Address email, String password)
      throws AuthorizationException, LoadingUsersException {

    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
      throw new AuthorizationException("User is already registered");
    }

    try {
      User toRegister = userAuthenticator.createUser(email, password);
      return userRepository.save(toRegister);
    } catch (SaveUserException | HashingFailedException e) {
      throw new AuthorizationException("Could not save user");
    }
  }
}
