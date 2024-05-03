package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.ExportMailboxException;
import java.io.File;

public interface MailboxExportRepository {

  File exportMailbox(ExportableMailbox mailbox) throws ExportMailboxException;
}
