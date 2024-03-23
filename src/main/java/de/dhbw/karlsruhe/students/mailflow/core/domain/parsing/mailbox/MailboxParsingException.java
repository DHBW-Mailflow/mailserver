package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox;

public class MailboxParsingException extends RuntimeException {
  public MailboxParsingException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
