package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export.MailboxExportUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class SelectMailboxTypeToExportCLIPrompt extends BaseCLIPrompt {

  private final MailboxExportUseCase mailboxExportUseCase;

  public SelectMailboxTypeToExportCLIPrompt(
      BaseCLIPrompt previousCLIPrompt, MailboxExportUseCase mailboxExportUseCase) {
    super(previousCLIPrompt);
    this.mailboxExportUseCase = mailboxExportUseCase;
  }

  @Override
  public void start() {
    super.start();
    BaseCLIPrompt baseCLIPrompt = showActionMenuPrompt();
    baseCLIPrompt.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {

    printDefault("Which mailbox type do you want to export?");
    for (MailboxType mailboxType : MailboxType.values()) {
      printDefault(mailboxType.toString());
    }
    return getPreviousPrompt();
  }
}
