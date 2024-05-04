package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.ExportMailboxException;

public interface MailboxExportRepository {

  void exportMailbox(ExportableMailbox mailbox) throws ExportMailboxException;
}
