package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxExportRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.ExportMailboxException;
import java.io.File;

public class MailboxExportService implements MailboxExportUseCase {

  private final MailboxRepository mailboxRepository;
  private final MailboxExportRepository mailboxExportRepository;

  public MailboxExportService(
      MailboxRepository mailboxRepository, MailboxExportRepository mailboxExportRepository) {
    this.mailboxRepository = mailboxRepository;
    this.mailboxExportRepository = mailboxExportRepository;
  }

  @Override
  public File exportMailbox(Address address, MailboxType type)
      throws ExportMailboxException, MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox = mailboxRepository.findByAddressAndType(address, type);

    if (mailbox == null) {
      throw new MailboxLoadingException("Mailbox could not be loaded");
    }
    ExportableMailbox exportableMailbox = convertToExportableMailbox(mailbox);
    return mailboxExportRepository.exportMailbox(exportableMailbox);
  }

  private ExportableMailbox convertToExportableMailbox(Mailbox mailbox) {
    return new ExportableMailbox(mailbox.getOwner(), mailbox.getType(), mailbox);
  }
}
