package de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums;

/**
 * The type a mailbox can have.
 * The SENT mailbox type for emails which the user has already sent.
 * The READ mailbox type for emails which the user received from others and has already read.
 */
public enum MailboxType {

  SENT("sent"),
  READ("read"),
  SPAM("spam"),
  DELETED("deleted"),
  UNREAD("unread"),
  DRAFT("draft");

  MailboxType(String fileSuffix) {
    this.fileSuffix = fileSuffix.toLowerCase();
  }

  private final String fileSuffix;

  public String getFileSuffix() {
    return fileSuffix;
  }
}
