package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jonas-Karl
 */
public class OrganizeEmailsCLIPrompt extends AuthorizedCLIPrompt {

  private final UCCollectionOrganizeEmails organizeUseCases;
  private final UCCollectionProvideEmails provideEmails;

  public OrganizeEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      UCCollectionProvideEmails provideEmails,
      UCCollectionOrganizeEmails organizeUseCases) {
    super(previousPrompt, authUseCase);
    this.provideEmails = provideEmails;
    this.organizeUseCases = organizeUseCases;
  }

  @Override
  public void start() {
    super.start();
    printDefault("Organize Emails");
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Mark as read",
        new MarkEmailsCLIPrompt(
            this,
            authUseCase,
            provideEmails.provideAllUnreadEmailsService(),
            organizeUseCases.markAsReadService()));
    promptMap.put(
        "Mark as unread",
        new MarkEmailsCLIPrompt(
            this,
            authUseCase,
            provideEmails.provideAllReadEmailsService(),
            organizeUseCases.markAsUnreadService()));
    return readUserInputWithOptions(promptMap);
  }
}
