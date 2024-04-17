package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchContentEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchEmailCLIPrompt extends AuthorizedCLIPrompt {

  private final UCCollectionSearchEmail searchEmails;


  public SearchEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase, UCCollectionSearchEmail searchEmails) {
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

  public String formatEmail(Email email) {
    return "%s: %s - %s"
        .formatted(
            email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Subject", new SearchSubjectCLIPrompt(this, authUseCase, searchEmails.searchSubjectEmailUseCase()));
    return readUserInputWithOptions(promptMap);
  }
}
