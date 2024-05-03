package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export.MailboxExportUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.ExportMailboxException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

public class ExportMailboxCLIPrompt extends BaseCLIPrompt {

  private final MailboxExportUseCase mailboxExportUseCase;

  private final MailboxType mailboxType;

  public ExportMailboxCLIPrompt(
      BaseCLIPrompt previousPrompt,
      MailboxExportUseCase mailboxExportUseCase,
      MailboxType mailboxType) {
    super(previousPrompt);
    this.mailboxExportUseCase = mailboxExportUseCase;
    this.mailboxType = mailboxType;
  }

  @Override
  public void start() {
    super.start();
    try {
      mailboxExportUseCase.exportMailbox(mailboxType);
      printDefault("Mailbox successfully exported");
    } catch (MailboxSavingException | ExportMailboxException | MailboxLoadingException e) {
      printWarning("Could not export mailbox");
    }
  }
}
