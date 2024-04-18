package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author seiferla
 */
public class ReadEmailCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  private final MarkEmailUseCase markEmailUseCase;
  private final boolean printContent;

  public ReadEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      Email email,
      MarkEmailUseCase markEmailUseCase,
      boolean printContent) {
    super(previousPrompt);
    this.email = email;
    this.markEmailUseCase = markEmailUseCase;
    this.printContent = printContent;
  }

  @Override
  public void start() {
    try {
      markEmailUseCase.mark(email);
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not mark email as read");
    }
    if (!printContent) {
      return;
    }
    printDefault(formatEmailContent(email));
  }

  public String formatEmailContent(Email email) {
    return email.getContent();
  }
}
