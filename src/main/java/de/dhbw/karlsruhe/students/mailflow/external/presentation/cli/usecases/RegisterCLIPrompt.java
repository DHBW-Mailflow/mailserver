package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

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
    super.start();

    String email = simplePrompt("What's your new email?");
    String password = simplePrompt("What's your new password?");
    try {
      registerUseCase.register(email, password);
    } catch (AuthorizationException | LoadingUsersException | SaveUserException e) {
      printWarning(e.getMessage());
    }
  }
}
