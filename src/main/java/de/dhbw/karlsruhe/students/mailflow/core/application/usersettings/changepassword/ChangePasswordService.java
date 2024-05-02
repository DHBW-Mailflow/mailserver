package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changepassword;

import java.util.Optional;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.PasswordHasher;

public class ChangePasswordService implements ChangePasswordUseCase {

  private final UserRepository repository;
  private final AuthSessionUseCase authSessionUseCase;

  public ChangePasswordService(UserRepository repository, AuthSessionUseCase authSessionUseCase) {
    this.repository = repository;
    this.authSessionUseCase = authSessionUseCase;
  }

  @Override
  public void changePassword(String newPassword)
      throws AuthorizationException, LoadingUsersException, SaveUserException,
      HashingFailedException {
    Optional<User> userFindResult =
        repository.findByEmail(authSessionUseCase.getSessionUserAddress());

    if (userFindResult.isEmpty()) {
      throw new IllegalStateException(
          "user must be found in repository when their password should be changed");
    }

    User user = userFindResult.get();

    String salt = PasswordHasher.generateSalt();

    user.setPassword(PasswordHasher.hashPassword(newPassword, salt));
    user.setSalt(salt);

    repository.save(user);
    // refresh user object
    authSessionUseCase.setSessionUser(user);
  }

}
