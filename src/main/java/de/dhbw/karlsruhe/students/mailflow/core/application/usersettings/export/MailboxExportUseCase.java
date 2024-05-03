package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.ExportMailboxException;
import java.io.File;

public interface MailboxExportUseCase {

  File exportMailbox(Address address, MailboxType type) throws ExportMailboxException, MailboxSavingException, MailboxLoadingException;
}
