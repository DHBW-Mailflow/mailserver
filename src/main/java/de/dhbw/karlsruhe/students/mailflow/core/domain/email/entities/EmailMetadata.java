package de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.Entity;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadataId;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;

/**
 * Representation of the e-mail metadata, consisting of the data subject,sender,
 * recipents,sentDate and the isRead status
 */
public class EmailMetadata extends Entity<EmailMetadataId> {
    private Subject subject;
    private Address sender;
    private Recipients recipients;
    private SentDate sentDate;
    private boolean isRead;

    private EmailMetadata(EmailMetadataId id, Subject subject, Address sender, Recipients recipients,
            SentDate sentDate) {
        super(id);
        this.subject = subject;
        this.sender = sender;
        this.recipients = recipients;
        this.sentDate = sentDate;
        this.isRead = false;
    }

    public static EmailMetadata create(Subject subject, Address sender, Recipients recipients, SentDate sentDate) {
        return new EmailMetadata(EmailMetadataId.createUnique(), subject, sender, recipients, sentDate);
    }

    public Subject getSubject() {
        return subject;
    }

    public Address getSender() {
        return sender;
    }

    public Recipients getRecipients() {
        return recipients;
    }

    public SentDate getSentDate() {
        return sentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
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