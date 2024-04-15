package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.AuthorizationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public final class RegisterCLIPrompt extends BaseCLIPrompt {
  private final RegisterUseCase registerUseCase;

  public RegisterCLIPrompt(RegisterUseCase registerUseCase) {
    this.registerUseCase = registerUseCase;
  }

  @Override
  public void start() throws MailboxSavingException, MailboxLoadingException {
    super.start();

    String email = simplePrompt("What's your new email?");
    String password = simplePrompt("What's your new password?");
    try {
      registerUseCase.register(Address.from(email), password);
    } catch (AuthorizationException | LoadingUsersException e) {
      printWarning(e.getMessage());
    }
  }
}
