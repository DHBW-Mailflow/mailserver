package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public interface AuthorizationService {
  boolean authorize(Address email, String password);
}
