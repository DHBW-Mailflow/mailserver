package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import java.util.List;

public record ExportableMailbox(String id, Address owner, MailboxType type, List<Email> emails) {}
