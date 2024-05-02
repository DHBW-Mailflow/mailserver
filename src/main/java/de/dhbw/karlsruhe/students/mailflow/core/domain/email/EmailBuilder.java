package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.List;

/**
 * @author jens1o, Jonas-Karl
 */
public final class EmailBuilder {

  private Subject subject;
  private Address sender;
  private List<Address> recipientsTo;
  private List<Address> recipientsCC;
  private List<Address> recipientsBCC;
  private String content;
  private List<Header> headers;
  private SentDate sentDate;
  private Email previousMail;

  public EmailBuilder() {
    this.content = "";
    this.subject = new Subject("");
    this.recipientsTo = List.of();
    this.recipientsCC = List.of();
    this.recipientsBCC = List.of();
    this.headers = List.of();
  }

  public Email build() {
    if (sentDate == null) {
      sentDate = SentDate.ofNow();
    }

    final Recipients recipients = new Recipients(recipientsTo, recipientsCC, recipientsBCC);
    final EmailMetadata emailMetadata =
        new EmailMetadata(subject, sender, headers, recipients, sentDate);
    return Email.create(content, emailMetadata, previousMail);
  }

  public EmailBuilder withSubject(Subject subject) {
    this.subject = subject;
    return this;
  }

  public EmailBuilder withSubject(String subject) {
    this.subject = new Subject(subject);
    return this;
  }

  public EmailBuilder withSender(Address sender) {
    this.sender = sender;
    return this;
  }

  public EmailBuilder withRecipientsTo(List<Address> recipientsTo) {
    this.recipientsTo = recipientsTo;
    return this;
  }

  public EmailBuilder withRecipientsCC(List<Address> recipientsCC) {
    this.recipientsCC = recipientsCC;
    return this;
  }

  public EmailBuilder withRecipientsBCC(List<Address> recipientsBCC) {
    this.recipientsBCC = recipientsBCC;
    return this;
  }

  public EmailBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public EmailBuilder withSentDate(SentDate sentDate) {
    this.sentDate = sentDate;
    return this;
  }

  public EmailBuilder withMetaData(EmailMetadata metadata) {
    this.recipientsBCC = metadata.recipients().bcc();
    this.recipientsCC = metadata.recipients().cc();
    this.recipientsTo = metadata.recipients().to();
    this.subject = metadata.subject();
    this.sender = metadata.sender();
    this.sentDate = metadata.sentDate();
    this.headers = metadata.headers();
    return this;
  }

  public EmailBuilder withPreviousMail(Email previousMail) {
    this.previousMail = previousMail;
    return this;
  }
}
