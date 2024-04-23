package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;

public interface RegisterUseCase {

  boolean register(String email, String password)
      throws AuthorizationException, LoadingUsersException, SaveUserException;
}
