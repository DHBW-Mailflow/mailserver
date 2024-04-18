package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserCreator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;

/**
 * @author Jonas-Karl
 */
public record UCCollectionAuth(
    AuthSessionUseCase authSession,
    LoginUseCase loginUseCase,
    LogoutUseCase logoutUseCase,
    RegisterUseCase registerUseCase) {

  public static UCCollectionAuth init(
      AuthSessionUseCase authSessionUseCase,
      UserRepository userRepository,
      PasswordChecker passwordChecker,
      UserCreator userCreator) {
    return new UCCollectionAuth(
        authSessionUseCase,
        new LoginService(authSessionUseCase, userRepository, passwordChecker),
        new LogoutService(authSessionUseCase),
        new RegistrationService(userRepository, userCreator));
  }
}
