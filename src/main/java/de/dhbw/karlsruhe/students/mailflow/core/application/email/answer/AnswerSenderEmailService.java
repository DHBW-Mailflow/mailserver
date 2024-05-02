package de.dhbw.karlsruhe.students.mailflow.core.application.email.answer;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

/**
 * @author Jonas-Karl
 */
public class AnswerSenderEmailService implements AnswerEmailUseCase {

  private final EmailSendUseCase sendUseCase;

  public AnswerSenderEmailService(EmailSendUseCase sendUseCase) {
    this.sendUseCase = sendUseCase;
  }

  @Override
  public void answer(Email emailToAnswer, String answerContent)
      throws InvalidRecipients, MailboxSavingException, MailboxLoadingException {
    sendUseCase.setToRecipients(emailToAnswer.getSender().toString());
    sendUseCase.setMessage(answerContent);
    if (emailToAnswer.getPreviousMail() == null) {
      sendUseCase.setSubject("RE: " + emailToAnswer.getSubject().subject());
    } else {
      sendUseCase.setSubject(emailToAnswer.getSubject().subject());
    }
    sendUseCase.setPreviousMail(emailToAnswer);
    sendUseCase.sendPreparedEmail();
  }
}
