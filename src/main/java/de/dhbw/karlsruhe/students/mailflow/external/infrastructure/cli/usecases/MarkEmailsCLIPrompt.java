package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails.ReadEmailCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MarkEmailsCLIPrompt extends AuthorizedCLIPrompt {

  private final MarkEmailUseCase markUseCase;
  private final ProvideEmailsUseCase provideEmailsUseCase;

  public MarkEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      AuthUseCase authUseCase,
      ProvideEmailsUseCase provideEmailsUseCase,
      MarkEmailUseCase markUseCase) {
    super(previousPrompt, authUseCase);
    this.markUseCase = markUseCase;
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public void start() {
    super.start();
    try {
      var filteredEmails = provideEmailsUseCase.provideEmails(authUseCase.getSessionUserAddress());
      BaseCLIPrompt baseCLIPrompt = showActionMenuPrompt(filteredEmails);
      baseCLIPrompt.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not emails");
    }
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    if (emailList.isEmpty()) {
      printDefault("No emails found");
      return getPreviousPrompt();
    }
    printDefault(
        "Which email do you want to mark as %s?".formatted(provideEmailsUseCase.getMailboxName()));
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(
          formatEmail(email), new ReadEmailCLIPrompt(this, email, markUseCase, authUseCase, false));
    }
    return readUserInputWithOptions(promptMap);
  }
}
