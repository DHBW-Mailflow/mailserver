package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.ExportMailboxException;
import java.io.File;

public interface MailboxExportUseCase {

  File exportMailbox(MailboxType type) throws ExportMailboxException, MailboxSavingException, MailboxLoadingException;
}
