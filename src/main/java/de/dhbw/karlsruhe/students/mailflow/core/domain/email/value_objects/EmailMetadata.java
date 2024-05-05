package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.List;
import java.util.Objects;

/**
 * Representation of constant e-mail, which will exist with the creation of an e-mail
 *
 * @author Jonas-Karl
 */
public final class EmailMetadata {
  private final Subject subject;
  private final Address sender;
  private final List<Header> headers;
  private final Recipients recipients;
  private final SentDate sentDate;

  /** */
  public EmailMetadata(
      Subject subject,
      Address sender,
      List<Header> headers,
      Recipients recipients,
      SentDate sentDate) {
    this.subject = subject;
    this.sender = sender;
    this.headers = headers;
    this.recipients = recipients;
    this.sentDate = sentDate;
  }

  public EmailMetadata withoutBCCRecipients() {
    return new EmailMetadata(subject, sender, headers, recipients.withoutBCCRecipients(), sentDate);
  }

  public Subject subject() {
    return subject;
  }

  public Address sender() {
    return sender;
  }

  public List<Header> headers() {
    return headers;
  }

  public Recipients recipients() {
    return recipients;
  }

  public SentDate sentDate() {
    return sentDate;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (EmailMetadata) obj;
    return Objects.equals(this.subject, that.subject)
        && Objects.equals(this.sender, that.sender)
        && Objects.equals(this.headers, that.headers)
        && Objects.equals(this.recipients, that.recipients)
        && Objects.equals(this.sentDate, that.sentDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subject, sender, headers, recipients, sentDate);
  }

  @Override
  public String toString() {
    return "EmailMetadata["
        + "subject="
        + subject
        + ", "
        + "sender="
        + sender
        + ", "
        + "headers="
        + headers
        + ", "
        + "recipients="
        + recipients
        + ", "
        + "sentDate="
        + sentDate
        + ']';
  }
}
