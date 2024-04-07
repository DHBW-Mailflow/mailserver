package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.LoadingUsersException;

/**
 * @author seiferla
 */
public interface RegisterUseCase {

  boolean register(Address email, String password)
      throws AuthorizationException, LoadingUsersException;
}
