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

  public ShowEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      UCCollectionProvideEmails provideEmails) {
    super(previousPrompt, authUseCase);
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
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Sent",
        new ShowEmailsCLIPrompt(this, authUseCase, provideEmails.provideSentEmailsUseCase()));
    promptMap.put(
        "Spam",
        new ShowEmailsCLIPrompt(this, authUseCase, provideEmails.provideSpamEmailsUseCase()));
    promptMap.put(
        "Deleted",
        new ShowEmailsCLIPrompt(this, authUseCase, provideEmails.provideDeletedEmailsUseCase()));
    promptMap.put(
        "Inbox unread",
        new ShowEmailsCLIPrompt(
            this, authUseCase, provideEmails.provideInboxUnreadEmailsUseCase()));
    promptMap.put(
        "Inbox read",
        new ShowEmailsCLIPrompt(this, authUseCase, provideEmails.provideInboxReadEmailsUseCase()));
    return readUserInputWithOptions(promptMap);
  }
}
