package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.ComposeEmailCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.OrganizeEmailsCLIPrompt;
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
  private final UCCollectionProvideEmails provideEmails;
  private final UCCollectionOrganizeEmails organizeEmails;

  public MainCLIPrompt(
      AuthUseCase authUseCase,
      RegisterUseCase registerUseCase,
      EmailSendUseCase emailSendUseCase,
      UCCollectionProvideEmails provideEmails,
      UCCollectionOrganizeEmails organizeEmails) {
    super(null);
    this.authUseCase = authUseCase;
    this.registerUseCase = registerUseCase;
    this.emailSendUseCase = emailSendUseCase;
    this.provideEmails = provideEmails;
    this.organizeEmails = organizeEmails;
  }

  private BaseCLIPrompt showRegisterOrEmailPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Register", new RegisterCLIPrompt(this, registerUseCase));
    promptMap.put("Login", new LoginCLIPrompt(this, authUseCase));
    return readUserInputWithOptions(promptMap);
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Logout", new LogoutCLIPrompt(this, authUseCase));
    promptMap.put("Send E-Mail", new ComposeEmailCLIPrompt(this, authUseCase, emailSendUseCase));
    promptMap.put(
        "Show E-Mails",
        new ShowEmailTypesCLIPrompt(
            this, authUseCase, provideEmails, organizeEmails.markAsReadService()));
    promptMap.put(
        "Organize E-Mails",
        new OrganizeEmailsCLIPrompt(this, authUseCase, provideEmails, organizeEmails));
    return readUserInputWithOptions(promptMap);
  }

  @Override
  public void start() {
    super.start();
    while (true) {

      while (!authUseCase.isLoggedIn()) {
        BaseCLIPrompt registerOrEmailPrompt = showRegisterOrEmailPrompt();
        registerOrEmailPrompt.start();
      }

      BaseCLIPrompt action = showActionMenuPrompt();
      action.start();
    }
  }
}
