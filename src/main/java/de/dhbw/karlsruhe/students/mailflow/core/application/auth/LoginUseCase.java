package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;

public interface LoginUseCase {

    User login(Address email, String password) throws AuthorizationException, LoadingUsersException;

    User login(String email, String password) throws AuthorizationException, LoadingUsersException;

    User getSessionUser();


}
