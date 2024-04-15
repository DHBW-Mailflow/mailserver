package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;

public class ShowUnreadEmailsCLIPrompt extends BaseCLIPrompt {

  private AuthUseCase authUseCase;
  private ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase;

  public ShowUnreadEmailsCLIPrompt(
      AuthUseCase authUseCase, ProvideUnreadEmailsUseCase provideUnreadEmailsUseCase) {
    this.authUseCase = authUseCase;
    this.provideUnreadEmailsUseCase = provideUnreadEmailsUseCase;
  }

  @Override
  public void start() {
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
      promptMap.put(formatEmail(email), new ShowEmailContentCLIPrompt(email));
    }
    return readUserInputWithOptions(promptMap);
  }
}
