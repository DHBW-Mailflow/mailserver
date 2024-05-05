package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.ExportMailboxException;

public interface ExportableMailboxRepository {

  void saveMailbox(ExportableMailbox mailbox) throws ExportMailboxException;
}
