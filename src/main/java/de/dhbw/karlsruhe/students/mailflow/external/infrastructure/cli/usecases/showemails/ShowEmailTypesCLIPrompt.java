package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
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
  private final MarkEmailUseCase markEmailUseCase;

  public ShowEmailTypesCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      UCCollectionProvideEmails provideEmails,
      MarkEmailUseCase markEmailUseCase) {
    super(previousPrompt, authUseCase);
    this.provideEmails = provideEmails;
    this.markEmailUseCase = markEmailUseCase;
  }

  @Override
  public void start() {
    super.start();
    printDefault("What type of emails do you want to see?");
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    int unreadEmails =
        provideEmails
            .provideInboxUnreadEmailsUseCase()
            .getEmailCount(authUseCase.getSessionUserAddress());
    int readEmails =
        provideEmails
            .provideInboxReadEmailsUseCase()
            .getEmailCount(authUseCase.getSessionUserAddress());
    int sentEmails =
        provideEmails.provideSentEmailsUseCase().getEmailCount(authUseCase.getSessionUserAddress());
    int spamEmails =
        provideEmails.provideSpamEmailsUseCase().getEmailCount(authUseCase.getSessionUserAddress());
    int deletedEmails =
        provideEmails
            .provideDeletedEmailsUseCase()
            .getEmailCount(authUseCase.getSessionUserAddress());

    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Sent (%s)".formatted(sentEmails),
        new ShowEmailsCLIPrompt(
            this, authUseCase, provideEmails.provideSentEmailsUseCase(), markEmailUseCase));
    promptMap.put(
        "Spam (%s)".formatted(spamEmails),
        new ShowEmailsCLIPrompt(
            this, authUseCase, provideEmails.provideSpamEmailsUseCase(), markEmailUseCase));
    promptMap.put(
        "Deleted (%s)".formatted(deletedEmails),
        new ShowEmailsCLIPrompt(
            this, authUseCase, provideEmails.provideDeletedEmailsUseCase(), markEmailUseCase));
    promptMap.put(
        "Inbox unread (%s)".formatted(unreadEmails),
        new ShowEmailsCLIPrompt(
            this, authUseCase, provideEmails.provideInboxUnreadEmailsUseCase(), markEmailUseCase));
    promptMap.put(
        "Inbox read (%s)".formatted(readEmails),
        new ShowEmailsCLIPrompt(
            this, authUseCase, provideEmails.provideInboxReadEmailsUseCase(), markEmailUseCase));
    return readUserInputWithOptions(promptMap);
  }
}
