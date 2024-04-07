package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import static de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.PasswordHasher.hashPassword;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import java.util.logging.Logger;

/**
 * @author seiferla
 */
public class LocalPasswordChecker implements PasswordChecker {
  @Override
  public boolean checkPassword(String password, User user) {
    if (user == null) return false;
    if (password == null) return false;
    try {
      return user.password().equals(hashPassword(password, user.salt()));
    } catch (HashingFailedException e) {
      Logger.getLogger(PasswordHasher.class.getName()).warning("Could not hash password");
      return false;
    }
  }
}
