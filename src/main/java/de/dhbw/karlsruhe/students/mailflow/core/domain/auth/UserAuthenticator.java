package de.dhbw.karlsruhe.students.mailflow.core.domain.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;

/**
 * @author seiferla
 */
public interface UserAuthenticator {

  User createUser(Address email, String password) throws HashingFailedException;
}
