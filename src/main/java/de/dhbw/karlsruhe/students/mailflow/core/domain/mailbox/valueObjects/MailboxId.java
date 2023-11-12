package de.dhbw.karlsruhe.students.mailflow.core.domain.mailbox.valueObjects;

import java.util.UUID;

public record MailboxId(UUID id) {
    public static MailboxId CreateUnique() {
        return new MailboxId(UUID.randomUUID());
    }
}
