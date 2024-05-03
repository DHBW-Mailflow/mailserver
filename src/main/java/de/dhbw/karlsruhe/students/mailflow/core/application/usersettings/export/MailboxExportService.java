package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxExportRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.ExportMailboxException;
import java.io.File;

public class MailboxExportService implements MailboxExportUseCase {

  private final MailboxRepository mailboxRepository;
  private final MailboxExportRepository mailboxExportRepository;
  private final AuthSessionUseCase authSession;

  public MailboxExportService(
      AuthSessionUseCase authSession,
      MailboxRepository mailboxRepository,
      MailboxExportRepository mailboxExportRepository) {
    this.mailboxRepository = mailboxRepository;
    this.mailboxExportRepository = mailboxExportRepository;
    this.authSession = authSession;
  }

  private ExportableMailbox convertToExportableMailbox(Mailbox mailbox) {
    return new ExportableMailbox(mailbox.getOwner(), mailbox.getType(), mailbox);
  }

  @Override
  public File exportMailbox(MailboxType type)
      throws ExportMailboxException, MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox =
        mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);

    if (mailbox == null) {
      throw new MailboxLoadingException("Mailbox could not be loaded");
    }
    ExportableMailbox exportableMailbox = convertToExportableMailbox(mailbox);
    return mailboxExportRepository.exportMailbox(exportableMailbox);
  }
}
