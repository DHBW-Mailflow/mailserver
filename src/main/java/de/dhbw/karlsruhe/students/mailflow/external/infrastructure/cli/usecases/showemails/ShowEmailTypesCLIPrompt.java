package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author seiferla
 */
public class ShowEmailTypesCLIPrompt extends AuthorizedCLIPrompt {

  private final UCCollectionProvideEmails provideEmails;

  public ShowEmailTypesCLIPrompt(AuthUseCase authUseCase, UCCollectionProvideEmails provideEmails) {
    super(authUseCase);
    this.provideEmails = provideEmails;
  }

  @Override
  public void start() {
    super.start();
    printDefault("What type of emails do you want to see?");
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    if (provideEmails == null) {
      // TODO this will be fixed by another PR
      printWarning("should return to previous CLIPrompt");
      return null;
    }

    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Sent", new ShowEmailsCLIPrompt(authUseCase, provideEmails.provideSentEmailsUseCase()));
    promptMap.put(
        "Spam", new ShowEmailsCLIPrompt(authUseCase, provideEmails.provideSpamEmailsUseCase()));
    promptMap.put(
        "Deleted",
        new ShowEmailsCLIPrompt(authUseCase, provideEmails.provideDeletedEmailsUseCase()));
    promptMap.put(
        "Inbox read",
        new ShowEmailsCLIPrompt(authUseCase, provideEmails.provideInboxReadEmailsUseCase()));
    promptMap.put(
        "Inbox unread",
        new ShowEmailsCLIPrompt(authUseCase, provideEmails.provideInboxUnreadEmailsUseCase()));
    return readUserInputWithOptions(promptMap);
  }
}
