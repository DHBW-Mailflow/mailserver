package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class RegisterCLIPrompt extends BaseCLIPrompt {
  private final RegisterUseCase registerUseCase;

  public RegisterCLIPrompt(BaseCLIPrompt previousPrompt, RegisterUseCase registerUseCase) {
    super(previousPrompt);
    this.registerUseCase = registerUseCase;
  }

  @Override
  public void start() {

    try {
      super.start();

      String email = simplePrompt("What's your new email?");
      String password = simplePrompt("What's your new password?");

      registerUseCase.register(email, password);
    } catch (AuthorizationException | LoadingUsersException e) {
      printWarning(e.getMessage());
    } catch (LoadSettingsException e) {
      printWarning("Could not load settings");
    }
  }
}
