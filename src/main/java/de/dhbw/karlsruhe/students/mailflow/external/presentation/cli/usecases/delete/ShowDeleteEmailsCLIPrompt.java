package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.delete;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.DeleteEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowDeleteEmailsCLIPrompt extends BaseCLIPrompt {

  private final DeleteEmailsUseCase deleteEmailsUseCase;
  private final ProvideEmailsUseCase provideEmailsUseCase;

  public ShowDeleteEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      ProvideEmailsUseCase provideEmailsUseCase,
      DeleteEmailsUseCase deleteEmailsUseCase) {
    super(previousPrompt);
    this.deleteEmailsUseCase = deleteEmailsUseCase;
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public void start() {

    super.start();
    try {
      var filteredEmails = provideEmailsUseCase.provideEmails();
      BaseCLIPrompt baseCLIPrompt = showActionMenuPrompt(filteredEmails);
      baseCLIPrompt.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not load emails");
    }
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> filteredEmails) {
    if (filteredEmails.isEmpty()) {
      printDefault("No emails found");
      return getPreviousPrompt();
    }
    printDefault("Which email do you want to delete?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : filteredEmails) {
      promptMap.put(
          formatEmailListing(email), new DeleteEmailCLIPrompt(this, email, deleteEmailsUseCase));
    }
    return readUserInputWithOptions(promptMap);
  }
}
