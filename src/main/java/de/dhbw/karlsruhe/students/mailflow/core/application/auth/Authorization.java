package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;

public class Authorization implements AuthorizationService{

  private final UserRepository userRepository;

  public Authorization(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean authorize(String email, String password) {
    return false;
  }
}
