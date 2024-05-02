package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changepassword.ChangePasswordUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class ChangePasswordCLIPrompt extends BaseCLIPrompt {

  private final ChangePasswordUseCase changePasswordUseCase;


  protected ChangePasswordCLIPrompt(BaseCLIPrompt previousPrompt,
      ChangePasswordUseCase changePasswordUseCase) {
    super(previousPrompt);
    this.changePasswordUseCase = changePasswordUseCase;
  }

  @Override
  public void start() {
    super.start();

    String newPassword = simplePrompt("Enter new password:");

    try {
      changePasswordUseCase.changePassword(newPassword);
      printDefault("Password updated successfully");
      getPreviousPrompt().start();
    } catch (AuthorizationException | LoadingUsersException | SaveUserException
        | HashingFailedException e) {
      printWarning("Failed to update password: %s".formatted(e.getMessage()));
    }
  }

}
