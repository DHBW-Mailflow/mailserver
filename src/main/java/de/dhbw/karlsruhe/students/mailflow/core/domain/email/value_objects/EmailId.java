package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.UUID;

/**
 * @author Jonas-Karl
 */
public record EmailId(UUID id) {
    public static EmailId createUnique() {
        return new EmailId(UUID.randomUUID());
    }
}
