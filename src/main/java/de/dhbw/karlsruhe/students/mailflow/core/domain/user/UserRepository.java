package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import java.util.Optional;

/**
 * @author seiferla
 */
public interface UserRepository {
  Optional<User> findByEmail(Address email) throws LoadingUsersException;
  boolean save(User user) throws SaveUserException, LoadingUsersException;
}
