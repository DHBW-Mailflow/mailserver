package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.UUID;

public record AttachmentId(UUID id) {
    public static AttachmentId createUnique() {
        return new AttachmentId(UUID.randomUUID());
    }
}
