package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.PasswordChecker;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.UserCreator;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import java.util.Objects;

/**
 * @author Jonas-Karl
 */
public final class UCCollectionAuth {
  private final AuthSessionUseCase authSession;
  private final LoginUseCase loginUseCase;
  private final LogoutUseCase logoutUseCase;
  private final RegisterUseCase registerUseCase;

  /** */
  public UCCollectionAuth(
      AuthSessionUseCase authSession,
      LoginUseCase loginUseCase,
      LogoutUseCase logoutUseCase,
      RegisterUseCase registerUseCase) {
    this.authSession = authSession;
    this.loginUseCase = loginUseCase;
    this.logoutUseCase = logoutUseCase;
    this.registerUseCase = registerUseCase;
  }

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

  public AuthSessionUseCase authSession() {
    return authSession;
  }

  public LoginUseCase loginUseCase() {
    return loginUseCase;
  }

  public LogoutUseCase logoutUseCase() {
    return logoutUseCase;
  }

  public RegisterUseCase registerUseCase() {
    return registerUseCase;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (UCCollectionAuth) obj;
    return Objects.equals(this.authSession, that.authSession)
        && Objects.equals(this.loginUseCase, that.loginUseCase)
        && Objects.equals(this.logoutUseCase, that.logoutUseCase)
        && Objects.equals(this.registerUseCase, that.registerUseCase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authSession, loginUseCase, logoutUseCase, registerUseCase);
  }

  @Override
  public String toString() {
    return "UCCollectionAuth["
        + "authSession="
        + authSession
        + ", "
        + "loginUseCase="
        + loginUseCase
        + ", "
        + "logoutUseCase="
        + logoutUseCase
        + ", "
        + "registerUseCase="
        + registerUseCase
        + ']';
  }
}
