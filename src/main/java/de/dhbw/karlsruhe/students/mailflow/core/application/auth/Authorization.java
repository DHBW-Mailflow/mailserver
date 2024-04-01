package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;

public class Authorization implements AuthorizationService{

  private final UserRepository userRepository;

  public Authorization(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean authorize(Address email, String password) {
    return userRepository.findByEmailAndPassword(email, password).isPresent();
  }
}
