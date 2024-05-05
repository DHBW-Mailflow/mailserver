package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @author seiferla, Jonas-Karl
 */
public final class ExportableEmail {
  private final String subject;
  private final String content;
  private final String senderAddress;
  private final ExportableRecipients recipient;
  private final LocalDateTime sendDate;
  private final boolean isRead;
  private final Map<String, String> header;

  /** */
  public ExportableEmail(
      String subject,
      String content,
      String senderAddress,
      ExportableRecipients recipient,
      LocalDateTime sendDate,
      boolean isRead,
      Map<String, String> header) {
    this.subject = subject;
    this.content = content;
    this.senderAddress = senderAddress;
    this.recipient = recipient;
    this.sendDate = sendDate;
    this.isRead = isRead;
    this.header = header;
  }

  public String subject() {
    return subject;
  }

  public String content() {
    return content;
  }

  public boolean isRead() {
    return isRead;
  }

  public Map<String, String> header() {
    return header;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (ExportableEmail) obj;
    return Objects.equals(this.subject, that.subject)
        && Objects.equals(this.content, that.content)
        && Objects.equals(this.senderAddress, that.senderAddress)
        && Objects.equals(this.recipient, that.recipient)
        && Objects.equals(this.sendDate, that.sendDate)
        && this.isRead == that.isRead
        && Objects.equals(this.header, that.header);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subject, content, senderAddress, recipient, sendDate, isRead, header);
  }

  @Override
  public String toString() {
    return "ExportableEmail["
        + "subject="
        + subject
        + ", "
        + "content="
        + content
        + ", "
        + "senderAddress="
        + senderAddress
        + ", "
        + "recipient="
        + recipient
        + ", "
        + "sendDate="
        + sendDate
        + ", "
        + "isRead="
        + isRead
        + ", "
        + "header="
        + header
        + ']';
  }
}
