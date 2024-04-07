package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import static de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.PasswordHasher.hashPassword;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserAuthenticator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;

public class LocalUserAuthenticator implements UserAuthenticator {

  @Override
  public User createUser(Address email, String password) throws HashingFailedException {
    String salt = PasswordHasher.generateSalt();
    return new User(email, hashPassword(password, salt), salt);
  }
}
