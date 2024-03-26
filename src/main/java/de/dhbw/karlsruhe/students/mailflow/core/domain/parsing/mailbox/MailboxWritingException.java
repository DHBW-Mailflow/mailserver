package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox;

public class MailboxWritingException extends Exception {

  public MailboxWritingException(String s, Exception exception) {
    super(s, exception);
  }
}
