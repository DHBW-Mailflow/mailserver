package de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums;

public enum MailboxType {
  COMMON("common"),
  SENT("sent"),
  INBOX("inbox"),
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
