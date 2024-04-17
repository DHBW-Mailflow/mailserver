package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchEmailCLIPrompt extends BaseCLIPrompt {

  private final AuthUseCase authUseCase;

  private final SearchEmailUseCase searchEmailUseCase;

  private final MailboxType mailboxType;

  public SearchEmailCLIPrompt(
      AuthUseCase authUseCase, SearchEmailUseCase searchEmailUseCase, MailboxType mailboxType) {
    this.authUseCase = authUseCase;
    this.searchEmailUseCase = searchEmailUseCase;
    this.mailboxType = mailboxType;
  }

  @Override
  public void start() {
    super.start();
    printDefault("Type the content you want to search for:");
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    try {
      List<Email> emailList =
          searchEmailUseCase.searchContentInEmails(
              s, authUseCase.getSessionUser().email(), mailboxType);
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
      promptMap.put(formatEmail(email), new ShowSearchEmailContentCLIPrompt(email));
    }

    return readUserInputWithOptions(promptMap);
  }
}
