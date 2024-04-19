package de.dhbw.karlsruhe.students.mailflow.core.application.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

public interface EmailSendUseCase {

  void sendPreparedEmail()
      throws MailboxLoadingException, MailboxSavingException, InvalidRecipients;

  void validateRecipients() throws InvalidRecipients;

  void setToRecipients(String toRecipientsString) throws IllegalArgumentException;

  void setCCRecipients(String ccRecipientsString) throws IllegalArgumentException;

  void setBCCRecipients(String bccRecipientsString) throws IllegalArgumentException;

  void setSubject(String subject);

  void setMessage(String message);
}
