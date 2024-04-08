package de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums;

/**
 * The type a mailbox can have. This is used to categorize emails.
 *
 * @author Jonas-Karl
 */
public enum MailboxType {
  /** The INBOX mailbox type for emails which the user has received. */
  INBOX("inbox"),
  /** The SENT mailbox type for emails which the user has already sent. */
  SENT("sent"),

  /** The SPAM_READ mailbox type for emails which where categorized as SPAM */
  SPAM("spam"),

  /**
   * The DELETED mailbox type for emails which where deleted by the user. They will be removed in
   * the future.
   */
  DELETED("deleted"),

  /** The DRAFT mailbox type for emails which the user created but did not yet send. */
  DRAFT("draft");

  private final String fileSuffix;

  /**
   * @param fileSuffix defined by an enum value.
   */
  MailboxType(String fileSuffix) {
    this.fileSuffix = fileSuffix;
  }

  public String getStoringName() {
    return fileSuffix.toLowerCase();
  }
}
