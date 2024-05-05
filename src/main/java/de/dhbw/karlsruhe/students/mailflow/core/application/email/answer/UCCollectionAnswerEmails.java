package de.dhbw.karlsruhe.students.mailflow.core.application.email.answer;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendUseCase;
import java.util.Objects;

/**
 * @author Jonas-Karl
 */
public final class UCCollectionAnswerEmails {
  private final AnswerSenderEmailService answerSenderEmailService;

  /** */
  public UCCollectionAnswerEmails(AnswerSenderEmailService answerSenderEmailService) {
    this.answerSenderEmailService = answerSenderEmailService;
  }

  public static UCCollectionAnswerEmails init(EmailSendUseCase emailSendUseCase) {
    // Also AnswerAllEmailsService in the future
    return new UCCollectionAnswerEmails(new AnswerSenderEmailService(emailSendUseCase));
  }

  public AnswerSenderEmailService answerSenderEmailService() {
    return answerSenderEmailService;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (UCCollectionAnswerEmails) obj;
    return Objects.equals(this.answerSenderEmailService, that.answerSenderEmailService);
  }

  @Override
  public int hashCode() {
    return Objects.hash(answerSenderEmailService);
  }

  @Override
  public String toString() {
    return "UCCollectionAnswerEmails["
        + "answerSenderEmailService="
        + answerSenderEmailService
        + ']';
  }
}
