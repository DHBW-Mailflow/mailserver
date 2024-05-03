package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

/**
 * @author seiferla
 */
public class ReadEmailCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  private final MarkEmailUseCase markEmailUseCase;

  public ReadEmailCLIPrompt(
      BaseCLIPrompt previousPrompt, Email email, MarkEmailUseCase markEmailUseCase) {
    super(previousPrompt);
    this.email = email;
    this.markEmailUseCase = markEmailUseCase;
  }

  @Override
  public void start() {
    super.start();
    try {
      markEmailUseCase.mark(email);
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not mark email as read");
    }
  }
}
