package de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.Entity;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.AttachmentId;

/**
 * Representation of an e-mail attachment, consisting of the data bytes, contenttype and filename
 */
public class Attachment extends Entity<AttachmentId> {
    private String filename;
    private byte[] content;
    private String contentType;

    private Attachment(AttachmentId id, String filename, byte[] content, String contentType) {
        super(id);
        this.filename = filename;
        this.content = content;
        this.contentType = contentType;
    }

    public static Attachment create(String filename, byte[] content, String contentType) {
        return new Attachment(AttachmentId.createUnique(), filename, content, contentType);
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
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