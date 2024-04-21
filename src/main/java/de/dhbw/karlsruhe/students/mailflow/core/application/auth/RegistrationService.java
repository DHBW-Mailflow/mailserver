package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserCreator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import java.util.Optional;

/**
 * @author seiferla
 */
public class RegistrationService implements RegisterUseCase {

    private final UserRepository userRepository;

    private final UserCreator userCreator;

    public RegistrationService(UserRepository userRepository, UserCreator userCreator) {
        this.userRepository = userRepository;
        this.userCreator = userCreator;
    }

  @Override
  public boolean register(String email, String password)
      throws AuthorizationException, LoadingUsersException {
    Address address = Address.from(email);
    Optional<User> user = userRepository.findByEmail(address);
        if (user.isPresent()) {
            throw new AuthorizationException("User is already registered");
        }

        try {
      User toRegister = userCreator.createUser(address, password);
            return userRepository.save(toRegister);
        } catch (SaveUserException | HashingFailedException e) {
            throw new AuthorizationException("Could not save user");
        }
    }
}
