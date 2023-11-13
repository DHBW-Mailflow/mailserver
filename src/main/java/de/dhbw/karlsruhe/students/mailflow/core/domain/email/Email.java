package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import java.util.Date;
import java.util.List;
import java.util.Set;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.Attachment;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailId;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;

/**
 * Representation of an e-mail as AggregateRoot
 */
public final class Email extends AggregateRoot<EmailId> {
    private String subject;
    private Address sender;

    private Recipients recipients;

    private String content;
    private SentDate sentDate;
    private boolean isRead;
    private List<Header> headers;
    private Set<Attachment> attachments;

    private Email(EmailId id, String subject, Address sender, Recipients recipients, String content, SentDate sentDate,
            List<Header> headers, Set<Attachment> attachments) {
        super(id);
        this.subject = subject;
        this.sender = sender;
        this.recipients = recipients;
        this.content = content;
        this.sentDate = sentDate;
        this.sentDate = new SentDate(new Date(0));
        this.headers = headers;
        this.attachments = attachments;
        this.isRead = false;
    }

    public static Email create(String subject, Address sender, Recipients recipients, String content, SentDate sentDate,
            List<Header> headers, Set<Attachment> attachments) {
        return new Email(EmailId.createUnique(), subject, sender, recipients, content, sentDate, headers, attachments);
    }

    public String getSubject() {
        return subject;
    }

    public Address getSender() {
        return sender;
    }

    public Recipients getRecipients() {
        return recipients;
    }

    public String getContent() {
        return content;
    }

    public SentDate getSentDate() {
        return sentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public List<Header> getHeaders() {
        return headers;
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
}