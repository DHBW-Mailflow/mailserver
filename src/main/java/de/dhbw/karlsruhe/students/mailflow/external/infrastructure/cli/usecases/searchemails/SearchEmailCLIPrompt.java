package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails.ReadEmailCLIPrompt;
import java.time.DateTimeException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seiferla , Jonas-Karl
 */
public class SearchEmailCLIPrompt extends BaseCLIPrompt {

  private final SearchEmailUseCase searchEmailUseCase;

  private final MarkEmailUseCase markEmailUseCase;

  private final String placeHolder;

  public SearchEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      SearchEmailUseCase searchEmailUseCase,
      MarkEmailUseCase markEmailUseCase) {
    super(previousPrompt);
    this.searchEmailUseCase = searchEmailUseCase;
    this.placeHolder = "";
    this.markEmailUseCase = markEmailUseCase;
  }

  public SearchEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      SearchEmailUseCase searchEmailUseCase,
      MarkEmailUseCase markEmailUseCase,
      String placeHolder) {
    super(previousPrompt);
    this.searchEmailUseCase = searchEmailUseCase;
    this.placeHolder = " (%s)".formatted(placeHolder);
    this.markEmailUseCase = markEmailUseCase;
  }

  @Override
  public void start() {
    super.start();
    try {
      String userInput = simplePrompt("Enter the content you want to search for" + placeHolder);
      List<Email> emailList = searchEmailUseCase.searchEmails(userInput);
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not load emails");
    } catch (DateTimeException e) {
      printWarning("Invalid date format");
    }
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(
          formatEmail(email), new ReadEmailCLIPrompt(this, email, markEmailUseCase, true));
    }
    return readUserInputWithOptions(promptMap);
  }
}
