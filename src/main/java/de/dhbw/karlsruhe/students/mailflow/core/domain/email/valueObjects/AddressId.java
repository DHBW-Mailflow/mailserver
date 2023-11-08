package de.dhbw.karlsruhe.students.mailflow.core.domain.email.valueObjects;

import java.util.UUID;

public record AddressId(UUID id) {
    public static AddressId CreateUnique() {
        return new AddressId(UUID.randomUUID());
    }
}
