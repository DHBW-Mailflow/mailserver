package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.ComposeEmailCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.RegisterCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails.ShowEmailTypesCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CLI-Entrypoint
 *
 * @author Jonas-Karl
 */
public final class MainCLIPrompt extends BaseCLIPrompt {
  private final AuthUseCase authUseCase;
  private final RegisterUseCase registerUseCase;
  private final EmailSendUseCase emailSendUseCase;
  private final ProvideEmailsUseCase provideEmailsUseCase;


  public MainCLIPrompt(AuthUseCase authUseCase, RegisterUseCase registerUseCase, EmailSendUseCase emailSendUseCase, ProvideEmailsUseCase provideEmailsUseCase) {
    this.authUseCase = authUseCase;
    this.registerUseCase = registerUseCase;
    this.emailSendUseCase = emailSendUseCase;
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  private BaseCLIPrompt showRegisterOrEmailPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Register", new RegisterCLIPrompt(registerUseCase));
    promptMap.put("Login", new LoginCLIPrompt(authUseCase));
    return readUserInputWithOptions(promptMap);
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Logout", new LogoutCLIPrompt(authUseCase));
    promptMap.put("Send E-Mail", new ComposeEmailCLIPrompt(authUseCase, emailSendUseCase));
    promptMap.put("Show emails", new ShowEmailTypesCLIPrompt(authUseCase,provideEmailsUseCase));
    return readUserInputWithOptions(promptMap);
  }

  @Override
  public void start() {
    super.start();

    while (authUseCase.getSessionUser() == null) {
      BaseCLIPrompt registerOrEmailPrompt = showRegisterOrEmailPrompt();
      registerOrEmailPrompt.start();
    }

    while (true) {
      BaseCLIPrompt action = showActionMenuPrompt();
      action.start();
    }
  }
}
