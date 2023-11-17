package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import java.util.List;
import java.util.Set;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Attachment;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailId;

/**
 * Representation of an e-mail as AggregateRoot
 */
public final class Email extends AggregateRoot<EmailId> {
    private EmailMetadata emailMetadata;
    private boolean isRead;
    private String content;
    private Set<Attachment> attachments;

    private Email(EmailId id, String content, EmailMetadata emailMetadata,
            Set<Attachment> attachments) {
        super(id);
        this.emailMetadata = emailMetadata;
        this.content = content;
        this.attachments = attachments;
    }

    public static Email create(String content, EmailMetadata emailMetadata,
            Set<Attachment> attachments) {
        return new Email(EmailId.createUnique(), content, emailMetadata, attachments);
    }

    public String getContent() {
        return content;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public boolean getRead() {
        return isRead;
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

    public EmailMetadata getEmailMetadata() {
        return emailMetadata;
    }
}