package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class LoginCLIPrompt extends BaseCLIPrompt {
  private final LoginUseCase loginUseCase;

  public LoginCLIPrompt(BaseCLIPrompt previousPrompt, LoginUseCase loginUseCase) {
    super(previousPrompt);
    this.loginUseCase = loginUseCase;
  }

  @Override
  public void start() {
    try {
      super.start();

      String loginEmailInput = simplePrompt("What is your email?");
      String loginPasswordInput = simplePrompt("What is your password?");

      loginUseCase.login(Address.from(loginEmailInput), loginPasswordInput);
    } catch (AuthorizationException | LoadingUsersException | IllegalArgumentException e) {
      printWarning(e.getMessage());
    } catch (LoadSettingsException e) {
      printWarning("Could not load settings");
    }
  }
}
