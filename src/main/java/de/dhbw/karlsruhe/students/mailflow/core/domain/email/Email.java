package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Attachment;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailId;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * Representation of an e-mail as AggregateRoot
 *
 * @author Jonas-Karl
 */
public final class Email extends AggregateRoot<EmailId> {
  private final EmailMetadata emailMetadata;
  private final String content;
  private final Set<Attachment> attachments;
  private final Email previousMail;

  private Email(
      EmailId id,
      String content,
      EmailMetadata emailMetadata,
      Set<Attachment> attachments,
      Email previousMail) {
    super(id);
    this.emailMetadata = emailMetadata;
    this.content = content;
    this.attachments = attachments;
    this.previousMail = previousMail;
  }

  public static Email create(
      String content,
      EmailMetadata emailMetadata,
      Email previousMail,
      Set<Attachment> attachments) {
    return new Email(EmailId.createUnique(), content, emailMetadata, attachments, previousMail);
  }

  public static Email create(
      String content, EmailMetadata emailMetadata, @Nullable Email previousMail) {
    return new Email(
        EmailId.createUnique(), content, emailMetadata, Collections.emptySet(), previousMail);
  }

  public static Email create(String content, EmailMetadata emailMetadata) {
    return new Email(EmailId.createUnique(), content, emailMetadata, Collections.emptySet(), null);
  }

  public String getContent() {
    return content;
  }

  public Set<Attachment> getAttachments() {
    return attachments;
  }

  @Override
  public boolean equals(Object other) {
    return super.equals(other);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public List<Address> getRecipientCC() {
    return this.emailMetadata.recipients().cc();
  }

  public List<Address> getRecipientBCC() {
    return this.emailMetadata.recipients().bcc();
  }

  public Subject getSubject() {
    return this.emailMetadata.subject();
  }

  public SentDate getSendDate() {
    return this.emailMetadata.sentDate();
  }

  public Address getSender() {
    return this.emailMetadata.sender();
  }

  public List<Header> getHeaders() {
    return this.emailMetadata.headers();
  }

  public List<Address> getRecipientTo() {
    return this.emailMetadata.recipients().to();
  }

  public Email withoutBCCRecipients() {
    return Email.create(getContent(), emailMetadata.withoutBCCRecipients(), previousMail);
  }

  public Email getPreviousMail() {
    return previousMail;
  }
}
