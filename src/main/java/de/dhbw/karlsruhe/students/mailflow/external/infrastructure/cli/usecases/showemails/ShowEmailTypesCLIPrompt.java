package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author seiferla
 */
public class ShowEmailTypesCLIPrompt extends AuthorizedCLIPrompt {
  private final ProvideEmailsUseCase provideEmailsUseCase;

  public ShowEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      ProvideEmailsUseCase provideEmailsUseCase) {
    super(previousPrompt, authUseCase);
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
    promptMap.put(
        "Spam",
        new ShowSpamEmailsCLIPrompt(this, authUseCase, provideEmailsUseCase, MailboxType.SPAM));
    promptMap.put(
        "Deleted",
        new ShowDeletedEmailsCLIPrompt(
            this, authUseCase, provideEmailsUseCase, MailboxType.DELETED));
    promptMap.put(
        "Inbox unread",
        new ShowUnreadInboxEmailsCLIPrompt(
            this, authUseCase, provideEmailsUseCase, MailboxType.INBOX));
    promptMap.put(
        "Inbox read",
        new ShowReadInboxEmailsCLIPrompt(
            this, authUseCase, provideEmailsUseCase, MailboxType.INBOX));
    return readUserInputWithOptions(promptMap);
  }
}
