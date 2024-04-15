package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.read;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.readmails.ProvideReadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.unread.ShowUnreadEmailContentCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowReadEmailsCLIPrompt extends BaseCLIPrompt {

  private final AuthUseCase authUseCase;

  private final ProvideReadEmailsUseCase provideReadEmailsUseCase;

  public ShowReadEmailsCLIPrompt(
      AuthUseCase authUseCase, ProvideReadEmailsUseCase provideReadEmailsUseCase) {
    this.authUseCase = authUseCase;
    this.provideReadEmailsUseCase = provideReadEmailsUseCase;
  }
  @Override
  public void start() throws MailboxSavingException, MailboxLoadingException {
    super.start();
    printDefault("Showing read emails");
    List<Email> emailList = provideReadEmailsUseCase.provideReadEmails(authUseCase.getSessionUser().email());
    BaseCLIPrompt action = showActionMenuPrompt(emailList);
    action.start();
  }

  public String formatEmail(Email email) {
    return "%s: %s - %s".formatted(email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    printDefault("Which email do you want to read?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(formatEmail(email), new ShowReadEmailContentCLIPrompt(email));
    }
    return readUserInputWithOptions(promptMap);
  }


}
