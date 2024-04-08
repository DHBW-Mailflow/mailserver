package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

import java.io.IOException;

/**
* @author Jonas-Karl
*/
public class MailboxSavingException extends Exception {

  public MailboxSavingException(String s) {
    super(s);
  }

  public MailboxSavingException(String s, IOException e) {
    super(s, e);
  }
}
