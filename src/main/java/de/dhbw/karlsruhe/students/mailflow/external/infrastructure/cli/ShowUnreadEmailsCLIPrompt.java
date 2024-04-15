package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.unreadmails.ProvideUnreadEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

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
    printDefault("Inbox Emails:");
    try {
      provideUnreadEmailsUseCase.provideUnreadEmails(authUseCase.getSessionUser().email()).forEach(email -> printDefault(email.toString()));
    } catch (MailboxSavingException | MailboxLoadingException e) {
      e.printStackTrace();
      printWarning("Could not load emails");
    }
  }
}
