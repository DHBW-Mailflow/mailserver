package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author seiferla
 */
public class ShowEmailTypesCLIPrompt extends BaseCLIPrompt {

  private final UCCollectionProvideEmails provideEmails;
  private final MarkEmailUseCase markEmailUseCase;
  private final UCCollectionAnswerEmails answerEmailUseCase;

  public ShowEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt,
      UCCollectionProvideEmails provideEmails,
      MarkEmailUseCase markEmailUseCase,
      UCCollectionAnswerEmails answerEmailUseCase) {
    super(previousPrompt);
    this.provideEmails = provideEmails;
    this.markEmailUseCase = markEmailUseCase;
    this.answerEmailUseCase = answerEmailUseCase;
  }

  @Override
  public void start() {
    super.start();
    printDefault("What type of emails do you want to see?");
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    int unreadEmails = provideEmails.provideInboxUnreadEmailsUseCase().getEmailCount();
    int readEmails = provideEmails.provideInboxReadEmailsUseCase().getEmailCount();
    int sentEmails = provideEmails.provideSentEmailsUseCase().getEmailCount();
    int spamEmails = provideEmails.provideSpamEmailsService().getEmailCount();
    int deletedEmails = provideEmails.provideDeletedEmailsUseCase().getEmailCount();

    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Sent (%s)".formatted(sentEmails),
        new ShowEmailsCLIPrompt(
            this, provideEmails.provideSentEmailsUseCase(), markEmailUseCase, answerEmailUseCase));
    promptMap.put(
        "Spam (%s)".formatted(spamEmails),
        new ShowEmailsCLIPrompt(
            this, provideEmails.provideSpamEmailsService(), markEmailUseCase, answerEmailUseCase));
    promptMap.put(
        "Deleted (%s)".formatted(deletedEmails),
        new ShowEmailsCLIPrompt(
            this,
            provideEmails.provideDeletedEmailsUseCase(),
            markEmailUseCase,
            answerEmailUseCase));
    promptMap.put(
        "Inbox unread (%s)".formatted(unreadEmails),
        new ShowEmailsCLIPrompt(
            this,
            provideEmails.provideInboxUnreadEmailsUseCase(),
            markEmailUseCase,
            answerEmailUseCase));
    promptMap.put(
        "Inbox read (%s)".formatted(readEmails),
        new ShowEmailsCLIPrompt(
            this,
            provideEmails.provideInboxReadEmailsUseCase(),
            markEmailUseCase,
            answerEmailUseCase));
    return readUserInputWithOptions(promptMap);
  }
}
