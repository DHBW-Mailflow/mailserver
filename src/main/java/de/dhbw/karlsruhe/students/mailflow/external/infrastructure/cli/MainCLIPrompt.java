package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.readmails.ProvideReadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.read.ShowReadEmailsCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.unread.ShowUnreadEmailsCLIPrompt;
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
  private final ProvideReadEmailsUseCase provideReadEmailsUseCase;

  public MainCLIPrompt(AuthUseCase authUseCase, RegisterUseCase registerUseCase, ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase,
      ProvideReadEmailsUseCase provideReadEmailsUseCase) {
    this.authUseCase = authUseCase;
    this.registerUseCase = registerUseCase;
    this.provideUnreadEmailsUseCase = provideUnreadEmailsUseCase;
    this.provideReadEmailsUseCase = provideReadEmailsUseCase;
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
    promptMap.put("Show read emails", new ShowReadEmailsCLIPrompt(authUseCase, provideReadEmailsUseCase));
    return readUserInputWithOptions(promptMap);
  }

  @Override
  public void start() throws MailboxSavingException, MailboxLoadingException {
    super.start();

    while (authUseCase.getSessionUser() == null) {
      BaseCLIPrompt registerOrEmailPrompt = showRegisterOrEmailPrompt();
      registerOrEmailPrompt.start();
    }

    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }
}
