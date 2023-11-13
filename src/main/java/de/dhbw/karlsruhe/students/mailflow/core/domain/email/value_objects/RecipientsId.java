package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.UUID;

public record RecipientsId(UUID id) {
    public static RecipientsId createUnique() {
        return new RecipientsId(UUID.randomUUID());
    }
}
