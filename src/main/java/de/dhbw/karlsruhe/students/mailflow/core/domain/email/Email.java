package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import java.util.List;
import java.util.Set;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.Attachment;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailId;

/**
 * Representation of an e-mail as AggregateRoot
 */
public final class Email extends AggregateRoot<EmailId> {
    private EmailMetadata emailMetadata;
    private String content;
    private List<Header> headers;
    private Set<Attachment> attachments;

    private Email(EmailId id, String content, List<Header> headers, EmailMetadata emailMetadata,
            Set<Attachment> attachments) {
        super(id);
        this.emailMetadata = emailMetadata;
        this.content = content;
        this.headers = headers;
        this.attachments = attachments;
    }

    public static Email create(String content, List<Header> headers, EmailMetadata emailMetadata,
            Set<Attachment> attachments) {
        return new Email(EmailId.createUnique(), content, headers, emailMetadata, attachments);
    }

    public String getContent() {
        return content;
    }

    public void setRead(boolean read) {
        emailMetadata.setRead(read);
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

    public EmailMetadata getEmailMetadata() {
        return emailMetadata;
    }
}