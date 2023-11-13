package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.UUID;

public record EmailMetadataId(UUID id) {
    public static EmailMetadataId createUnique() {
        return new EmailMetadataId(UUID.randomUUID());
    }
}