package de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities;

import java.sql.Date;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.Entity;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadataId;

/**
 * Representation of the e-mail metadata, consisting of the data subject,sender,
 * recipents,sentDate and the isRead status
 */
public class EmailMetadata extends Entity<EmailMetadataId> {
    private String subject;
    private Address sender;
    private Recipients recipients;
    private Date sentDate;
    private boolean isRead;

    private EmailMetadata(EmailMetadataId id, String subject, Address sender, Recipients recipients, Date sentDate) {
        super(id);
        this.subject = subject;
        this.sender = sender;
        this.recipients = recipients;
        this.sentDate = sentDate;
        this.isRead = false;
    }

    public static EmailMetadata create(String subject, Address sender, Recipients recipients, Date sentDate) {
        return new EmailMetadata(EmailMetadataId.createUnique(), subject, sender, recipients, sentDate);
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

    public Date getSentDate() {
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