package de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions;

/**
 * @author Jonas-Karl
 */
public class MailboxDoesNotExistException extends Exception {

  public MailboxDoesNotExistException(String s) {
    super(s);
  }
}
