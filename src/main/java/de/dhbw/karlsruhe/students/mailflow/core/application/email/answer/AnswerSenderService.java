package de.dhbw.karlsruhe.students.mailflow.core.application.email.answer;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.HeaderKey;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import java.util.List;
import java.util.stream.Stream;

public class AnswerSenderService implements AnswerEmailUseCase {

  private final EmailSendUseCase sendUseCase;

  public AnswerSenderService(EmailSendUseCase sendUseCase) {
    this.sendUseCase = sendUseCase;
  }

  @Override
  public void answer(Email emailToAnswer, String answerContent)
      throws InvalidRecipients, MailboxSavingException, MailboxLoadingException {
    List<Header> headers = getHeaders(emailToAnswer);
    sendUseCase.setToRecipients(emailToAnswer.getSender().toString());
    sendUseCase.setMessage(answerContent);
    sendUseCase.setHeaders(headers);
    sendUseCase.sendPreparedEmail();
  }

  private List<Header> getHeaders(Email emailToAnswer) {
    // extract data
    String[] previousInReply = emailToAnswer.getHeaderValues(HeaderKey.IN_REPLY_TO.getKeyName());
    String[] previousReferences = emailToAnswer.getHeaderValues(HeaderKey.REFERENCES.getKeyName());
    String[] previousIDs = emailToAnswer.getHeaderValues(HeaderKey.MESSAGE_ID.getKeyName());

    // define new headers
    Header newInReplyHeader = new Header(HeaderKey.IN_REPLY_TO.getKeyName(), previousIDs);
    String[] referencesArray =
        Stream.concat(Stream.of(previousInReply), Stream.of(previousReferences))
            .toArray(String[]::new);
    Header newReferences = new Header(HeaderKey.REFERENCES.getKeyName(), referencesArray);

    // save headers
    return List.of(newInReplyHeader, newReferences);
  }
}
