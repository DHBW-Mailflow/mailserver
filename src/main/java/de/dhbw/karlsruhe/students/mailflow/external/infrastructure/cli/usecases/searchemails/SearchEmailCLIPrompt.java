package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.UCCollectionSearchEmail;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject.SearchSubjectEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.List;

public class SearchEmailCLIPrompt extends AuthorizedCLIPrompt {

  private final SearchEmailUseCase searchEmailUseCase;

  public SearchEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      SearchEmailUseCase searchEmailUseCase) {
    super(previousPrompt, authUseCase);
    this.searchEmailUseCase = searchEmailUseCase;
  }



  @Override
  public void start() {
    super.start();
    try {
      List<Email> emailList =
          searchEmailUseCase.searchEmails(authUseCase.getSessionUserAddress()).;
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not read %s emails".formatted(mailboxString));
    }
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    return null;
  }

}
