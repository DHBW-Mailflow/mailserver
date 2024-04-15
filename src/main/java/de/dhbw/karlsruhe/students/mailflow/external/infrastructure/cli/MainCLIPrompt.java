package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.RegisterCLIPrompt;
import java.util.HashMap;
import java.util.Map;

/**
 * CLI-Entrypoint
 *
 * @author Jonas-Karl
 */
public final class MainCLIPrompt extends BaseCLIPrompt {
  private final AuthUseCase authUseCase;
  private final RegisterUseCase registerUseCase;
  private final ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase;

  public MainCLIPrompt(AuthUseCase authUseCase, RegisterUseCase registerUseCase, ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase) {
    this.authUseCase = authUseCase;
    this.registerUseCase = registerUseCase;
    this.provideUnreadEmailsUseCase = provideUnreadEmailsUseCase;
  }

  private BaseCLIPrompt showRegisterOrEmailPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new HashMap<>();
    promptMap.put("Register", new RegisterCLIPrompt(registerUseCase));
    promptMap.put("Login", new LoginCLIPrompt(authUseCase));
    return readUserInputWithOptions(promptMap);
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new HashMap<>();
    promptMap.put("Logout", new LogoutCLIPrompt(authUseCase));
    promptMap.put("Show unread emails", new ShowUnreadEmailsCLIPrompt(authUseCase, provideUnreadEmailsUseCase));
    return readUserInputWithOptions(promptMap);
  }

  @Override
  public void start() {
    super.start();

    while (authUseCase.getSessionUser() == null) {
      BaseCLIPrompt registerOrEmailPrompt = showRegisterOrEmailPrompt();
      registerOrEmailPrompt.start();
    }

    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }
}
