package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

import java.io.IOException;

public class MailboxParsingServiceException extends Exception{

  public MailboxParsingServiceException(String errorMessage) {
    super(errorMessage);
  }

  public MailboxParsingServiceException(String errorMessage, IOException e) {
    super(errorMessage,e);
  }
}
