package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.HelperParsing;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchEmailTypesCLIPrompt extends AuthorizedCLIPrompt {

  private final UCCollectionSearchEmail searchEmails;
  private final MarkEmailUseCase markEmailUseCase;


  public SearchEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      UCCollectionSearchEmail searchEmails,
      MarkEmailUseCase markEmailUseCase) {
    super(previousPrompt, authUseCase);
    this.searchEmails = searchEmails;
    this.markEmailUseCase = markEmailUseCase;
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
            this, authUseCase, searchEmails.searchSubjectEmailUseCase(), markEmailUseCase));
    promptMap.put(
        "Content",
        new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchContentEmailUseCase(), markEmailUseCase));
    promptMap.put(
        "Date After",
        new SearchEmailCLIPrompt(
            this,
            authUseCase,
            searchEmails.searchAfterDateEmailService(),
            markEmailUseCase,
            HelperParsing.DATE_FORMAT));
    promptMap.put(
        "Date Before",
        new SearchEmailCLIPrompt(
            this,
            authUseCase,
            searchEmails.searchBeforeDateEmailService(),
            markEmailUseCase,
            HelperParsing.DATE_FORMAT));
    promptMap.put(
        "Exact Date",
        new SearchEmailCLIPrompt(
            this,
            authUseCase,
            searchEmails.searchEqualDateEmailService(),
            markEmailUseCase,
            HelperParsing.DATE_FORMAT));
    promptMap.put(
        "Sender",
        new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchSenderEmailService(), markEmailUseCase));
    promptMap.put(
        "Recipients",
        new SearchEmailCLIPrompt(
            this, authUseCase, searchEmails.searchRecipientEmailService(), markEmailUseCase));

    return readUserInputWithOptions(promptMap);
  }
}
