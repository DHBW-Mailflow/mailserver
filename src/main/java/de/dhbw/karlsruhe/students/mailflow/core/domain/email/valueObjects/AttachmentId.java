package de.dhbw.karlsruhe.students.mailflow.core.domain.email.valueObjects;

import java.util.UUID;

public record AttachmentId(UUID id) {
    public static AttachmentId CreateUnique() {
        return new AttachmentId(UUID.randomUUID());
    }
}
