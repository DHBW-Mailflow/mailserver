package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changepassword;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;

public interface ChangePasswordUseCase {
  void changePassword(Address user, String newPassword)
      throws AuthorizationException, LoadingUsersException, SaveUserException,
      HashingFailedException;
}
