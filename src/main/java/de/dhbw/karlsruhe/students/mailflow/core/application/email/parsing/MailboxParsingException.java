package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

public class MailboxParsingException extends RuntimeException {
  public MailboxParsingException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}