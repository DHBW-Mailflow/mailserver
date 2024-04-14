package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.RegisterCLIPrompt;
import java.util.HashMap;
import java.util.Map;

public class CLIPromptServer extends AbstractCLIPrompt implements Server {
  private final AuthUseCase authUseCase;
  private final RegisterUseCase registerUseCase;

  public CLIPromptServer(AuthUseCase authUseCase, RegisterUseCase registerUseCase) {
    this.authUseCase = authUseCase;
    this.registerUseCase = registerUseCase;
  }

  private AbstractCLIPrompt showRegisterOrEmailPrompt() {
    System.out.println("What do you want to do?");
    Map<String, AbstractCLIPrompt> promptMap = new HashMap<>();
    promptMap.put("Register", new RegisterCLIPrompt(registerUseCase));
    promptMap.put("Login", new LoginCLIPrompt(authUseCase));
    return readUserInputWithOptions(promptMap);
  }

  private AbstractCLIPrompt showActionMenuPrompt() {
    System.out.println("What do you want to do?");
    Map<String, AbstractCLIPrompt> promptMap = new HashMap<>();
    promptMap.put("Logout", new LogoutCLIPrompt(authUseCase));
    return readUserInputWithOptions(promptMap);
  }

  @Override
  public void start() {
    while (authUseCase.getSessionUser() == null) {
      AbstractCLIPrompt registerOrEmailPrompt = showRegisterOrEmailPrompt();
      registerOrEmailPrompt.start();
    }

    AbstractCLIPrompt action = showActionMenuPrompt();
    action.start();
  }
}
