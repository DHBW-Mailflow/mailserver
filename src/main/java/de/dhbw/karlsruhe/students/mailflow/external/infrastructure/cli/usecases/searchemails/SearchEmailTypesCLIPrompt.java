package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchEmailTypesCLIPrompt extends AuthorizedCLIPrompt {

  private final UCCollectionSearchEmail searchEmails;
  private final ProvideEmailsUseCase provideEmailsUseCase;

  public SearchEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      UCCollectionSearchEmail searchEmails,
      ProvideEmailsUseCase provideEmailsUseCase) {
    super(previousPrompt, authUseCase);
    this.searchEmails = searchEmails;
    this.provideEmailsUseCase = provideEmailsUseCase;
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
            this, authUseCase, searchEmails.searchSubjectEmailUseCase(), provideEmailsUseCase));
    promptMap.put(
        "Content",
        new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchContentEmailUseCase(), provideEmailsUseCase));
    promptMap.put("Date After", new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchAfterDateEmailService(), provideEmailsUseCase));
    promptMap.put("Date Before", new SearchEmailCLIPrompt( this, authUseCase, searchEmails.searchBeforeDateEmailService(), provideEmailsUseCase));
    promptMap.put("Exact Date", new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchEqualDateEmailService(), provideEmailsUseCase)
    promptMap.put("Sender", new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchSenderEmailService(), provideEmailsUseCase));
    promptMap.put("Recipients", new SearchEmailCLIPrompt(this, authUseCase, searchEmails.searchRecipientEmailService(), provideEmailsUseCase));

    return readUserInputWithOptions(promptMap);
  }
}
