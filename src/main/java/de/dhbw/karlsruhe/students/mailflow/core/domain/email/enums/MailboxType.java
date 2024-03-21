package de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums;

/**
 * The type a mailbox can have.
 * The SENT mailbox type for emails which the user has already sent.
 * The READ mailbox type for emails which the user received from others and has already read.
 * The UNREAD mailbox type for emails which the user received from others and are not already read.
 * The SPAM_READ mailbox type for emails which where categorized as SPAM and are read.
 * The SPAM_UNREAD mailbox type for emails which where categorized as SPAM and are not already read.
 * The DELETED mailbox type for emails which where deleted by the user. They will be removed in the future.
 * The DRAFT mailbox type for emails which the user created but did not yet send.
 */
public enum MailboxType {

  SENT("sent"),
  READ("read"),
  UNREAD("unread"),
  SPAM_READ("spam-read"),
  SPAM_UNREAD("spam-unread"),
  DELETED("deleted"),
  DRAFT("draft");

/**
*
 * @param fileSuffix fitting to an enum value.
*/
  MailboxType(String fileSuffix) {
    this.fileSuffix = fileSuffix.toLowerCase();
  }

  private final String fileSuffix;

  public String getFileSuffix() {
    return fileSuffix;
  }
}
