package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.unread;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowUnreadEmailsCLIPrompt extends BaseCLIPrompt {

  private final AuthUseCase authUseCase;
  private final ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase;

  public ShowUnreadEmailsCLIPrompt(
      AuthUseCase authUseCase, ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase) {
    this.authUseCase = authUseCase;
    this.provideUnreadEmailsUseCase = provideUnreadEmailsUseCase;
  }

  @Override
  public void start() throws MailboxSavingException, MailboxLoadingException {
    super.start();
    printDefault("Inbox Emails:");
    try {
      List<Email> emailList = provideUnreadEmailsUseCase.provideUnreadEmails(authUseCase.getSessionUser().email());
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();

    } catch (MailboxSavingException | MailboxLoadingException e) {
      e.printStackTrace();
      printWarning("Could not load emails");
    }
  }

  public String formatEmail(Email email) {
    return "%s: %s - %s".formatted(email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    printDefault("Which email do you want to read?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(formatEmail(email), new ShowUnreadEmailContentCLIPrompt(email, provideUnreadEmailsUseCase, authUseCase));
    }
    return readUserInputWithOptions(promptMap);
  }
}
