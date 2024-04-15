package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author seiferla
 */
public class ShowEmailTypesCLIPrompt extends BaseCLIPrompt {
  private final AuthUseCase authUseCase;
  private final ProvideEmailsUseCase provideEmailsUseCase;

  public ShowEmailTypesCLIPrompt(
      AuthUseCase authUseCase, ProvideEmailsUseCase provideEmailsUseCase) {
    this.authUseCase = authUseCase;
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public void start() {
    super.start();
    printDefault("What type of emails do you want to see?");
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Spam", new ShowSpamEmailsCLIPrompt(authUseCase, provideEmailsUseCase));
    promptMap.put("Deleted", new ShowDeletedEmailsCLIPrompt(authUseCase, provideEmailsUseCase));
    promptMap.put(
        "Inbox unread", new ShowUnreadInboxEmailsCLIPrompt(authUseCase, provideEmailsUseCase));
    promptMap.put(
        "Inbox read", new ShowReadInboxEmailsCLIPrompt(authUseCase, provideEmailsUseCase));
    return readUserInputWithOptions(promptMap);
  }
}
