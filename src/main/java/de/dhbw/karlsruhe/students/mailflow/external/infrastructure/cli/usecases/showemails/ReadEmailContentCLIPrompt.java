package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author seiferla
 */
public class ReadEmailContentCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  private final MarkEmailUseCase markEmailUseCase;
  private final UCCollectionAnswerEmails answerEmails;

  public ReadEmailContentCLIPrompt(
      BaseCLIPrompt previousPrompt,
      Email email,
      MarkEmailUseCase markEmailUseCase,
      UCCollectionAnswerEmails answerEmails) {
    super(previousPrompt);
    this.email = email;
    this.markEmailUseCase = markEmailUseCase;
    this.answerEmails = answerEmails;
  }

  @Override
  public void start() {
    super.start();
    try {
      markEmailUseCase.mark(email);
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not mark email as read");
    }
    printDefault(formatEmailContent(email));

    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Answer Sender",
        new AnswerSenderCLIPrompt(this, answerEmails.answerSenderEmailService(), email));
    return readUserInputWithOptions(promptMap);
  }
}
