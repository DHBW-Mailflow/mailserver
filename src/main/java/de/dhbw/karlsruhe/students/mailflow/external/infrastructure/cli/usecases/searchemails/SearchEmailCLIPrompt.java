package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails.ShowEmailContentCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchEmailCLIPrompt extends AuthorizedCLIPrompt {

  private final SearchEmailUseCase searchEmailUseCase;

  private final ProvideEmailsUseCase provideEmailsUseCase;

  public SearchEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      SearchEmailUseCase searchEmailUseCase,
      ProvideEmailsUseCase provideEmailsUseCase) {
    super(previousPrompt, authUseCase);
    this.searchEmailUseCase = searchEmailUseCase;
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public void start() {
    super.start();
    try {
      String userInput = simplePrompt("Enter the content you want to search for");
      List<Email> emailList =
          searchEmailUseCase.searchEmails(userInput, authUseCase.getSessionUserAddress());
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not load emails");
    }
  }

  private String formatEmail(Email email) {
    return "%s: %s - %s"
        .formatted(
            email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(
          formatEmail(email),
          new ShowEmailContentCLIPrompt(this, email, provideEmailsUseCase, authUseCase));
    }
    return readUserInputWithOptions(promptMap);
  }
}
