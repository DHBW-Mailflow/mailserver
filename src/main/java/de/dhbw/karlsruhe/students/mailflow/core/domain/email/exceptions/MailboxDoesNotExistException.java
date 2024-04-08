package de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions;

public class MailboxDoesNotExistException extends Exception {

  public MailboxDoesNotExistException(String s) {
    super(s);
  }
}
