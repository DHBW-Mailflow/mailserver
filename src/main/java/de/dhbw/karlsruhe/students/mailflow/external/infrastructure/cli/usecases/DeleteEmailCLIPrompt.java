package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.delete.DeleteEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class DeleteEmailCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  private final DeleteEmailsUseCase deleteEmailsUseCase;

  public DeleteEmailCLIPrompt(
      BaseCLIPrompt previousPrompt, Email email, DeleteEmailsUseCase deleteEmailsUseCase) {
    super(previousPrompt);
    this.email = email;
    this.deleteEmailsUseCase = deleteEmailsUseCase;
  }

  @Override
  public void start() {
    try {
      deleteEmailsUseCase.delete(email);
      printDefault("Email deleted");
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not delete email");
    }
  }
}
