package de.dhbw.karlsruhe.students.mailflow.core.domain.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;

public interface UserCreator {

  User createUser(Address email, String password, UserSettings userSettings) throws HashingFailedException;
}
