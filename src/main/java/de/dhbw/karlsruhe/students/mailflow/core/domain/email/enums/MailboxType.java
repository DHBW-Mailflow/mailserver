package de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums;

/**
 * The type a mailbox can have. This is used to categorize emails.
 *
 * @author Jonas-Karl
 */
public enum MailboxType {

  /** The SENT mailbox type for emails which the user has already sent. */
  SENT("sent"),

  /** The READ mailbox type for emails which the user received from others and has already read. */
  READ("read"),

  /**
   * The UNREAD mailbox type for emails which the user received from others and are not already read
   */
  UNREAD("unread"),

  /** The SPAM_READ mailbox type for emails which where categorized as SPAM and are read. */
  SPAM_READ("spam-read"),

  /**
   * The SPAM_UNREAD mailbox type for emails which where categorized as SPAM and are not already
   * read.
   */
  SPAM_UNREAD("spam-unread"),

  /**
   * The DELETED mailbox type for emails which where deleted by the user. They will be removed in
   * the future.
   */
  DELETED("deleted"),

  /** The DRAFT mailbox type for emails which the user created but did not yet send. */
  DRAFT("draft");

  /**
   * @param fileSuffix defined by an enum value.
   */
  MailboxType(String fileSuffix) {
    this.fileSuffix = fileSuffix;
  }

  private final String fileSuffix;

  public String getStoringName() {
    return fileSuffix.toLowerCase();
  }
}
