package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import java.util.Optional;

public interface UserRepository {
  Optional<User> findByEmail(String email);
}
