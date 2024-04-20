package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

/**
 * @author Jonas-Karl
 */
public class InvalidRecipients extends Exception {
  public InvalidRecipients(String errorMessage) {
    super(errorMessage);
  }
}
