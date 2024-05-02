package de.dhbw.karlsruhe.students.mailflow.core.application.email.answer;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendUseCase;

/**
 * @author Jonas-Karl
 */
public record UCCollectionAnswerEmails(AnswerSenderEmailService answerSenderEmailService) {
  public static UCCollectionAnswerEmails init(EmailSendUseCase emailSendUseCase) {
    // Also AnswerAllEmailsService in the future
    return new UCCollectionAnswerEmails(new AnswerSenderEmailService(emailSendUseCase));
  }
}
