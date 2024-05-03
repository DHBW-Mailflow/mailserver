package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.AnswerEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

/**
 * @author Jonas-Karl
 */
public class AnswerSenderCLIPrompt extends BaseCLIPrompt {

  private final AnswerEmailUseCase answerEmailUseCase;
  private final Email emailToAnswer;

  public AnswerSenderCLIPrompt(
      BaseCLIPrompt previousPrompt, AnswerEmailUseCase answerEmailUseCase, Email emailToAnswer) {
    super(previousPrompt);
    this.answerEmailUseCase = answerEmailUseCase;
    this.emailToAnswer = emailToAnswer;
  }

  @Override
  public void start() {
    super.start();
    printDefault(
        "Please write the message to answer %s".formatted(emailToAnswer.getSender().toString()));
    String answerContent = readMultilineUserInput();
    try {
      answerEmailUseCase.answer(emailToAnswer, answerContent);
      printDefault("Email sent successfully!");
    } catch (MailboxLoadingException | MailboxSavingException e) {
      printWarning("Could not answer email");
    } catch (InvalidRecipients e) {
      printWarning("Could not answer email. The sender could not be determined");
    }
  }
}
