package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.UCCollectionAuth;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.job.WorkerQueue;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.UCCollectionSettings;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.ComposeEmailCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.OrganizeEmailsCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.RegisterCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.preferences.SettingsCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.searchemails.SearchEmailTypesCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.showemails.ShowEmailTypesCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CLI-Entrypoint
 *
 * @author Jonas-Karl
 */
public final class MainCLIPrompt extends BaseCLIPrompt {
  private final UCCollectionAuth collectionAuth;
  private final EmailSendUseCase emailSendUseCase;
  private final UCCollectionProvideEmails provideEmails;
  private final UCCollectionOrganizeEmails organizeEmails;
  private final UCCollectionSearchEmail searchEmails;
  private final UCCollectionSettings collectionSettings;
  private final UCCollectionAnswerEmails answerEmails;

  public MainCLIPrompt(
      UCCollectionAuth collectionAuth,
      EmailSendUseCase emailSendUseCase,
      UCCollectionProvideEmails provideEmails,
      UCCollectionOrganizeEmails organizeEmails,
      UCCollectionSearchEmail searchEmails,
      UCCollectionSettings collectionSettings,
      UCCollectionAnswerEmails answerEmails) {
    super(null);
    this.collectionAuth = collectionAuth;
    this.emailSendUseCase = emailSendUseCase;
    this.provideEmails = provideEmails;
    this.organizeEmails = organizeEmails;
    this.searchEmails = searchEmails;
    this.collectionSettings = collectionSettings;
    this.answerEmails = answerEmails;
  }

  private BaseCLIPrompt showRegisterOrEmailPrompt() {
    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Register", new RegisterCLIPrompt(this, collectionAuth.registerUseCase()));
    promptMap.put("Login", new LoginCLIPrompt(this, collectionAuth.loginUseCase()));
    return readUserInputWithOptions(promptMap);
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    int allEmails = provideEmails.provideAllEmailsService().getEmailCount();

    printDefault("What do you want to do?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put("Logout", new LogoutCLIPrompt(this, collectionAuth.logoutUseCase()));
    promptMap.put(
        "Send E-Mail", new ComposeEmailCLIPrompt(this, emailSendUseCase, collectionSettings));
    promptMap.put(
        "Show E-Mails (%s)".formatted(allEmails),
        new ShowEmailTypesCLIPrompt(
            this, provideEmails, organizeEmails.markAsReadService(), answerEmails));
    promptMap.put(
        "Organize E-Mails", new OrganizeEmailsCLIPrompt(this, provideEmails, organizeEmails));
    promptMap.put(
        "Search E-Mails",
        new SearchEmailTypesCLIPrompt(
            this, searchEmails, organizeEmails.markAsReadService(), answerEmails));
    promptMap.put("Preferences", new SettingsCLIPrompt(this, collectionSettings));
    return readUserInputWithOptions(promptMap);
  }

  @Override
  public void start() {
    super.start();
    while (true) {
      while (!collectionAuth.authSession().isLoggedIn()) {
        BaseCLIPrompt registerOrEmailPrompt = showRegisterOrEmailPrompt();
        registerOrEmailPrompt.start();
      }

      WorkerQueue.getInstance().performDueJobs();

      BaseCLIPrompt action = showActionMenuPrompt();
      action.start();
    }
  }
}
