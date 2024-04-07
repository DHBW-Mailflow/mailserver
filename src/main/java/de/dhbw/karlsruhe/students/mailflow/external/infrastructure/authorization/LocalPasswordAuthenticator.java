package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import static de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.PasswordHasher.hashPassword;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordAuthenticator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import java.util.logging.Logger;

public class LocalPasswordAuthenticator implements PasswordAuthenticator {

  @Override
  public boolean checkPassword(String password, User user) {
    try {
      return user.password().equals(hashPassword(password, user.salt()));
    } catch (HashingFailedException e) {
      Logger.getLogger(PasswordHasher.class.getName()).warning("Could not hash password");
      return false;
    }
  }
}
