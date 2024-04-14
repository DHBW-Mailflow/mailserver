package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.SendEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.server.Server;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.CreateEmailCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.RegisterCLIPrompt;
import java.util.HashMap;
import java.util.Map;

public class CLIPromptServer extends AbstractCLIPrompt implements Server {
  private final LoginUseCase loginUseCase;
  private final RegisterUseCase registerUseCase;
  private final SendEmailUseCase sendEmailUseCase;

  public CLIPromptServer(
      LoginUseCase loginUseCase,
      RegisterUseCase registerUseCase,
      SendEmailUseCase sendEmailUseCase) {
    this.loginUseCase = loginUseCase;
    this.registerUseCase = registerUseCase;
    this.sendEmailUseCase = sendEmailUseCase;
  }

    private AbstractCLIPrompt showRegisterOrEmailPrompt() {
        System.out.println("What do you want to do?");
        Map<String, AbstractCLIPrompt> promptMap = new HashMap<>();
        promptMap.put("Register", new RegisterCLIPrompt(registerUseCase));
        promptMap.put("Login", new LoginCLIPrompt(loginUseCase));
        return readUserInputWithOptions(promptMap);
    }


    private AbstractCLIPrompt showActionMenuPrompt() {
        System.out.println("What do you want to do?");
        Map<String, AbstractCLIPrompt> promptMap = new HashMap<>();
    promptMap.put("Send email", new CreateEmailCLIPrompt(loginUseCase, sendEmailUseCase));
    promptMap.put("Logout", new LogoutCLIPrompt());
        return readUserInputWithOptions(promptMap);
    }


    @Override
    public void start() {
        while (loginUseCase.getSessionUser() == null) {
            AbstractCLIPrompt registerOrEmailPrompt = showRegisterOrEmailPrompt();
            registerOrEmailPrompt.start();
        }

        AbstractCLIPrompt action = showActionMenuPrompt();
        action.start();
    }
}
