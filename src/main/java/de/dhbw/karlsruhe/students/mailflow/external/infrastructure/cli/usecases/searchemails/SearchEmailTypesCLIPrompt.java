package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.HelperParsing;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author seiferla , Jonas-Karl
 */
public class SearchEmailTypesCLIPrompt extends BaseCLIPrompt {

  private final UCCollectionSearchEmail searchEmails;
  private final MarkEmailUseCase markEmailUseCase;
  private final UCCollectionAnswerEmails answerEmailUseCase;

  public SearchEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt,
      UCCollectionSearchEmail searchEmails,
      MarkEmailUseCase markEmailUseCase,
      UCCollectionAnswerEmails answerEmailUseCase) {
    super(previousPrompt);
    this.searchEmails = searchEmails;
    this.markEmailUseCase = markEmailUseCase;
    this.answerEmailUseCase = answerEmailUseCase;
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
            this, searchEmails.searchSubjectEmailUseCase(), markEmailUseCase, answerEmailUseCase));
    promptMap.put(
        "Content",
        new SearchEmailCLIPrompt(
            this, searchEmails.searchContentEmailUseCase(), markEmailUseCase, answerEmailUseCase));
    promptMap.put(
        "Date After",
        new SearchEmailCLIPrompt(
            this,
            searchEmails.searchAfterDateEmailService(),
            markEmailUseCase,
            answerEmailUseCase,
            HelperParsing.DATE_FORMAT));
    promptMap.put(
        "Date Before",
        new SearchEmailCLIPrompt(
            this,
            searchEmails.searchBeforeDateEmailService(),
            markEmailUseCase,
            answerEmailUseCase,
            HelperParsing.DATE_FORMAT));
    promptMap.put(
        "Exact Date",
        new SearchEmailCLIPrompt(
            this,
            searchEmails.searchEqualDateEmailService(),
            markEmailUseCase,
            answerEmailUseCase,
            HelperParsing.DATE_FORMAT));
    promptMap.put(
        "Sender",
        new SearchEmailCLIPrompt(
            this, searchEmails.searchSenderEmailService(), markEmailUseCase, answerEmailUseCase));
    promptMap.put(
        "Recipients",
        new SearchEmailCLIPrompt(
            this,
            searchEmails.searchRecipientEmailService(),
            markEmailUseCase,
            answerEmailUseCase));

    return readUserInputWithOptions(promptMap);
  }
}
