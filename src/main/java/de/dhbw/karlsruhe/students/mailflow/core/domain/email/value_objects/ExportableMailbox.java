package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;

public record ExportableMailbox(Address owner, MailboxType type, Mailbox mailbox) {}
