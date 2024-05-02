package de.dhbw.karlsruhe.students.mailflow.core.application.email.send;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

public interface EmailSendUseCase {

  void sendPreparedEmail()
      throws MailboxLoadingException, MailboxSavingException, InvalidRecipients;

  void validateRecipients() throws InvalidRecipients;

  void setToRecipients(String toRecipientsString) throws InvalidRecipients;

  void setCCRecipients(String ccRecipientsString) throws InvalidRecipients;

  void setBCCRecipients(String bccRecipientsString) throws InvalidRecipients;

  void setSubject(String subject);

  void setMessage(String message);

  void setPreviousMail(Email emailToAnswer);
}
