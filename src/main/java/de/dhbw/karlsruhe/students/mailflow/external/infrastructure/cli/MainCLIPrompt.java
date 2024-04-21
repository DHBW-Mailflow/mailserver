package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.UCCollectionAuth;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.UCCollectionOrganizeEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.UCCollectionProvideEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.UCCollectionSettings;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.ComposeEmailCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LoginCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.LogoutCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.OrganizeEmailsCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.RegisterCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails.SearchEmailTypesCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails.ShowEmailTypesCLIPrompt;
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

  public MainCLIPrompt(
      UCCollectionAuth collectionAuth,
      EmailSendUseCase emailSendUseCase,
      UCCollectionProvideEmails provideEmails,
      UCCollectionOrganizeEmails organizeEmails,
      UCCollectionSearchEmail searchEmails,
      UCCollectionSettings collectionSettings) {
    super(null);
    this.collectionAuth = collectionAuth;
    this.emailSendUseCase = emailSendUseCase;
    this.provideEmails = provideEmails;
    this.organizeEmails = organizeEmails;
    this.searchEmails = searchEmails;
    this.collectionSettings = collectionSettings;
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
    promptMap.put("Send E-Mail", new ComposeEmailCLIPrompt(this, emailSendUseCase, collectionSettings));
    promptMap.put(
        "Show E-Mails (%s)".formatted(allEmails),
        new ShowEmailTypesCLIPrompt(this, provideEmails, organizeEmails.markAsReadService()));
    promptMap.put(
        "Organize E-Mails", new OrganizeEmailsCLIPrompt(this, provideEmails, organizeEmails));
    promptMap.put(
        "Search E-Mails",
        new SearchEmailTypesCLIPrompt(this, searchEmails, organizeEmails.markAsReadService()));
    promptMap.put(
        "Preferences", new SettingsCLIPrompt(this, collectionSettings));
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

      BaseCLIPrompt action = showActionMenuPrompt();
      action.start();
    }
  }
}
