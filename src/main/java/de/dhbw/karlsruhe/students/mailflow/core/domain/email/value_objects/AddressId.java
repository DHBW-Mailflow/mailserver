package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.UUID;

public record AddressId(UUID id) {
    public static AddressId createUnique() {
        return new AddressId(UUID.randomUUID());
    }
}
