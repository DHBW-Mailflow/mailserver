package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

public class MailboxLoadingException extends Exception {

  public MailboxLoadingException(String s) {
    super(s);
  }

  public MailboxLoadingException(String s, Exception e) {
    super(s, e);
  }
}
