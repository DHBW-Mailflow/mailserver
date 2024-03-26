package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import java.util.Optional;

public class UserRepoImpl implements UserRepository{

  @Override
  public Optional<User> findByEmail(String email) {

    // fetch user from database

    return Optional.empty();
  }
}
