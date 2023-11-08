package de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.Entity;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.valueObjects.AttachmentId;

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
        return new Attachment(AttachmentId.CreateUnique(), filename, content, contentType);
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
}