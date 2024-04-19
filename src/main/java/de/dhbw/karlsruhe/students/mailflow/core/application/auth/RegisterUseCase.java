package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;

public interface RegisterUseCase {

  boolean register(String email, String password)
      throws AuthorizationException, LoadingUsersException;
}
