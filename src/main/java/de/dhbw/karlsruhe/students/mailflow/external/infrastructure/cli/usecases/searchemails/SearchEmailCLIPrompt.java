package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.SearchContentEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchEmailCLIPrompt extends AuthorizedCLIPrompt {

  private final SearchContentEmailUseCase searchEmailUseCase;

  public SearchEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      SearchContentEmailUseCase searchEmailUseCase) {
    super(previousPrompt, authUseCase);
    this.searchEmailUseCase = searchEmailUseCase;
  }

  @Override
  public void start() {
    super.start();
    printDefault("Type the content you want to search for:");
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    try {
      List<Email> emailList =
          searchEmailUseCase.searchContentInEmails(s, authUseCase.getSessionUserAddress());
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      throw new RuntimeException(e);
    }
  }

  public String formatEmail(Email email) {
    return "%s: %s - %s"
        .formatted(
            email.getSender(), email.getSubject().subject(), email.getSendDate().formattedDate());
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {

    printDefault("Search results - Select an email to view:");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(formatEmail(email), new ShowSearchEmailContentCLIPrompt(this, authUseCase, email));
    }

    return readUserInputWithOptions(promptMap);
  }
}
