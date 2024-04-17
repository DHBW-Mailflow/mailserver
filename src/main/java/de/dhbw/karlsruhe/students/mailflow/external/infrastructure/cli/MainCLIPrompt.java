package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.RegisterUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchContentEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.ComposeEmailCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.RegisterCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails.SearchEmailCLIPrompt;
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
  private final UCCollectionSearchEmail searchEmails;

  public MainCLIPrompt(
      AuthUseCase authUseCase,
      RegisterUseCase registerUseCase,
      EmailSendUseCase emailSendUseCase,
      UCCollectionProvideEmails provideEmails, UCCollectionSearchEmail searchEmails) {
    super(null);
    this.authUseCase = authUseCase;
    this.registerUseCase = registerUseCase;
    this.emailSendUseCase = emailSendUseCase;
    this.provideEmails = provideEmails;
    this.searchEmails = searchEmails;

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
    promptMap.put("Search E-Mails", new SearchEmailCLIPrompt(this, authUseCase, searchEmails));
    promptMap.put(
        "Show emails", new ShowEmailTypesCLIPrompt(this, authUseCase, provideEmails));
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
