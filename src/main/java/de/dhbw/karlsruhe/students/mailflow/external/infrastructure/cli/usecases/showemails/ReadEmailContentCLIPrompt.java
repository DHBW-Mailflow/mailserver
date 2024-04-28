package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails.answer.AnswerSenderCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seiferla
 */
public class ReadEmailContentCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  private final MarkEmailUseCase markEmailUseCase;
  private final boolean printContent = true;
  private final UCCollectionAnswerEmails answerEmails;
  private final ProvideEmailsUseCase provideEmailsUseCase;

  public ReadEmailContentCLIPrompt(
      BaseCLIPrompt previousPrompt,
      Email email,
      MarkEmailUseCase markEmailUseCase,
      UCCollectionAnswerEmails answerEmails,
      ProvideEmailsUseCase provideEmailsUseCase) {
    super(previousPrompt);
    this.email = email;
    this.markEmailUseCase = markEmailUseCase;
    this.answerEmails = answerEmails;
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public void start() {
    super.start();
    markAsRead();
    final List<Email> previousEmails = getPreviousMails();
    printDefault(formatEmailContent(email, previousEmails));
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private void markAsRead() {
    try {
      markEmailUseCase.mark(email);
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not mark email as read");
    }
  }

  private List<Email> getPreviousMails() {
    try {
      return provideEmailsUseCase.provideEmails();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("could not retrieve previous emails");
    }
    return List.of();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Answer Sender",
        new AnswerSenderCLIPrompt(this, answerEmails.answerSenderEmailService(), email));
    return readUserInputWithOptions(promptMap);
  }
}
