package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchEmailTypesCLIPrompt extends AuthorizedCLIPrompt {

  private final UCCollectionSearchEmail searchEmails;

  public SearchEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt, AuthUseCase authUseCase, UCCollectionSearchEmail searchEmails) {
    super(previousPrompt, authUseCase);
    this.searchEmails = searchEmails;
  }

  @Override
  public void start() {
    super.start();
    printDefault("Which metadata do you want to search for in the mail?");
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Subject",
        new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchSubjectEmailUseCase()));
    promptMap.put(
        "Content",
        new SearchEmailTypesCLIPrompt(this, authUseCase, searchEmails.searchContentEmailUseCase()));
    return readUserInputWithOptions(promptMap);
  }
}
