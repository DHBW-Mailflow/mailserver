package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox;

public class MailboxDoesNotExistException extends Exception {

  public MailboxDoesNotExistException(String s) {
    super(s);
  }
}
