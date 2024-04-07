package de.dhbw.karlsruhe.students.mailflow.core.domain.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;

/**
 * @author seiferla
 */
public interface PasswordChecker {

  boolean checkPassword(String password, User user);
}
