package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxExportRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableEmails;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.ExportMailboxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MailboxExportService implements ExportUseCase {

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
    List<Email> readEmails = mailbox.getEmailsWithLabel(Label.READ);

    List<Email> unreadEmails = mailbox.getEmailsWithLabel(Label.UNREAD);

    List<ExportableEmails> exportableReadEmails = getExportableEmails(readEmails, true);

    List<ExportableEmails> exportableUnreadEmails = getExportableEmails(unreadEmails, false);

    List<ExportableEmails> allExportableEmails =
        Stream.concat(exportableReadEmails.stream(), exportableUnreadEmails.stream()).toList();

    LocalDateTime exportedDate = LocalDateTime.now();

    return new ExportableMailbox(
        mailbox.getOwner().toString(),
        mailbox.getType().toString(),
        allExportableEmails,
        exportedDate);
  }

  private List<ExportableEmails> getExportableEmails(List<Email> readEmails, boolean isRead) {
    List<ExportableEmails> exportableEmailsList = new ArrayList<>();
    for (Email email : readEmails) {
      Map<String, String> headerMap =
          email.getHeader().stream().collect(Collectors.toMap(Header::name, Header::value));
      ExportableRecipients exportableRecipients =
          new ExportableRecipients(
              email.getRecipientBCC().stream().map(Address::toString).toList(),
              email.getRecipientCC().stream().map(Address::toString).toList(),
              email.getRecipientTo().stream().map(Address::toString).toList());

      ExportableEmails exportableEmails =
          new ExportableEmails(
              email.getSubject().subject(),
              email.getContent(),
              email.getSender().toString(),
              exportableRecipients,
              email.getSendDate().date(),
              isRead,
              headerMap);
      exportableEmailsList.add(exportableEmails);
    }
    return exportableEmailsList;
  }

  @Override
  public void exportMailbox(MailboxType type)
      throws ExportMailboxException, MailboxSavingException, MailboxLoadingException {
    Mailbox mailbox =
        mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);

    if (mailbox == null) {
      throw new MailboxLoadingException("Mailbox could not be loaded");
    }
    ExportableMailbox exportableMailbox = convertToExportableMailbox(mailbox);
    mailboxExportRepository.exportMailbox(exportableMailbox);
  }
}
