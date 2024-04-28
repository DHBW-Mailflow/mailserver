package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jonas-Karl
 */
public class OrganizeEmailsCLIPrompt extends BaseCLIPrompt {

  private final UCCollectionOrganizeEmails organizeUseCases;
  private final UCCollectionProvideEmails provideEmails;
  private final UCCollectionAnswerEmails answerEmails;

  public OrganizeEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      UCCollectionProvideEmails provideEmails,
      UCCollectionOrganizeEmails organizeUseCases,
      UCCollectionAnswerEmails answerEmails) {
    super(previousPrompt);
    this.provideEmails = provideEmails;
    this.organizeUseCases = organizeUseCases;
    this.answerEmails = answerEmails;
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
            provideEmails.provideAllUnreadEmailsService(),
            organizeUseCases.markAsReadService(),
            answerEmails));
    promptMap.put(
        "Mark as unread",
        new MarkEmailsCLIPrompt(
            this,
            provideEmails.provideAllReadEmailsService(),
            organizeUseCases.markAsUnreadService(),
            answerEmails));
    promptMap.put(
        "Mark as spam",
        new MarkEmailsCLIPrompt(
            this,
            provideEmails.provideAllInboxEmailsService(),
            organizeUseCases.markAsSpamService()));
    promptMap.put(
        "Mark as not spam",
        new MarkEmailsCLIPrompt(
            this,
            provideEmails.provideSpamEmailsService(),
            organizeUseCases.markAsNotSpamService()));
    return readUserInputWithOptions(promptMap);
  }
}
