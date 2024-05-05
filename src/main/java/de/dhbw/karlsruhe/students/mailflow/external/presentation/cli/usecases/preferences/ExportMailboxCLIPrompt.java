package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export.ExportUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.ExportMailboxException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

public class ExportMailboxCLIPrompt extends BaseCLIPrompt {

  private final ExportUseCase exportUseCase;

  private final MailboxType mailboxType;

  public ExportMailboxCLIPrompt(
      BaseCLIPrompt previousPrompt, ExportUseCase exportUseCase, MailboxType mailboxType) {
    super(previousPrompt);
    this.exportUseCase = exportUseCase;
    this.mailboxType = mailboxType;
  }

  @Override
  public void start() {
    super.start();
    try {
      exportUseCase.exportMailbox(mailboxType);
      printDefault("Mailbox successfully exported");
    } catch (MailboxSavingException | ExportMailboxException | MailboxLoadingException e) {
      printWarning("Could not export mailbox: " + e.getMessage());
    }
  }
}
