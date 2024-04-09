package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Attachment;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailId;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;

/**
 * Representation of an e-mail as AggregateRoot
 *
 * @author Jonas-Karl
 */
public final class Email extends AggregateRoot<EmailId> {
    private final EmailMetadata emailMetadata;
    private final String content;
    private final Set<Attachment> attachments;
    private final List<Address> recipientCC;

    private Email(EmailId id, String content, EmailMetadata emailMetadata,
            Set<Attachment> attachments) {
        super(id);
        this.emailMetadata = emailMetadata;
        this.content = content;
        this.attachments = attachments;
        this.recipientCC = new ArrayList<>();
    }

    public static Email create(String content, EmailMetadata emailMetadata,
            Set<Attachment> attachments) {
        return new Email(EmailId.createUnique(), content, emailMetadata, attachments);
    }

    public static Email create(String content, EmailMetadata emailMetadata) {
        return new Email(EmailId.createUnique(), content, emailMetadata, Collections.emptySet());
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

    public EmailMetadata getEmailMetadata() {
        return emailMetadata;
    }

    public List<Address> getRecipientCC() {
        return this.recipientCC;
    }
}
