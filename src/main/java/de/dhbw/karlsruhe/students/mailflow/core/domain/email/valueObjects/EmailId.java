package de.dhbw.karlsruhe.students.mailflow.core.domain.email.valueObjects;

import java.util.UUID;

public record EmailId(UUID id) {
    public static EmailId CreateUnique() {
        return new EmailId(UUID.randomUUID());
    }
}
