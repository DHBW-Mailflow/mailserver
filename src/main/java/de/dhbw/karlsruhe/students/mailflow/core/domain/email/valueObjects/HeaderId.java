package de.dhbw.karlsruhe.students.mailflow.core.domain.email.valueObjects;

import java.util.UUID;

public record HeaderId(UUID id) {
    public static HeaderId createUnique() {
        return new HeaderId(UUID.randomUUID());
    }
}