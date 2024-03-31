package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.Optional;
/**
 * Repository interface for User entities.
 */
public interface UserRepository {
  Optional<User> findByEmailAndPassword(Address email, String password);
}
