package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export.MailboxExportUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

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
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    printDefault("Which mailbox type do you want to export?");
    for (MailboxType mailboxType : MailboxType.values()) {
      promptMap.put(
          mailboxType.toString(),
          new ExportMailboxCLIPrompt(this, mailboxExportUseCase, mailboxType));
    }
    return readUserInputWithOptions(promptMap);
  }
}
