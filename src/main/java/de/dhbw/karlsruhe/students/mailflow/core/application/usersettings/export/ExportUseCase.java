package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.ExportMailboxException;

public interface ExportUseCase {

  void exportMailbox(MailboxType type) throws ExportMailboxException, MailboxSavingException, MailboxLoadingException;
}
