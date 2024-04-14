package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;

import java.util.Optional;

/**
 * @author seiferla
 */
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;

    private final PasswordChecker passwordChecker;
    private User sessionUser;

    public LoginService(UserRepository userRepository, PasswordChecker passwordChecker) {
        this.userRepository = userRepository;
        this.passwordChecker = passwordChecker;
    }

    @Override
    public User login(Address email, String password)
            throws AuthorizationException, LoadingUsersException {

        return authorizeUser(email, password);
    }

    @Override
    public User login(String email, String password)
            throws AuthorizationException, LoadingUsersException {
        try {
            Address address = Address.from(email);
            return authorizeUser(address, password);
        } catch (IllegalArgumentException e) {
            throw new AuthorizationException("Credentials are incorrect. " + e.getMessage());
        }
    }

    private User authorizeUser(Address address, String password) throws LoadingUsersException, AuthorizationException {
        Optional<User> foundUser = userRepository.findByEmail(address);

        if (foundUser.isEmpty()) {
            throw new AuthorizationException("Credentials are incorrect");
        }

        var user = foundUser.get();

        if (!passwordChecker.checkPassword(password, user)) {
            throw new AuthorizationException("Credentials are incorrect");
        }

        this.sessionUser = user;
        return user;
    }

    @Override
    public User getSessionUser() {
        return sessionUser;
    }
}
