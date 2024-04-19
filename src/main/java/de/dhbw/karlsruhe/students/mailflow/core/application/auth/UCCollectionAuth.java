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
      AuthSessionUseCase authSession,
      UserRepository userRepository,
      PasswordChecker passwordChecker,
      UserCreator userCreator) {
    return new UCCollectionAuth(
        authSession,
        new LoginService(authSession, userRepository, passwordChecker),
        new LogoutService(authSession),
        new RegistrationService(userRepository, userCreator));
  }
}
