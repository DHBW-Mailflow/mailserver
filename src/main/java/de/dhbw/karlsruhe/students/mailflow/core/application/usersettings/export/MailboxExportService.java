package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.ExportableMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableEmail;
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
/**
 * @author seiferla, Jonas-Karl
 */
public class MailboxExportService implements ExportUseCase {

  private final MailboxRepository mailboxRepository;
  private final ExportableMailboxRepository exportableMailboxRepository;
  private final AuthSessionUseCase authSession;

  public MailboxExportService(
      AuthSessionUseCase authSession,
      MailboxRepository mailboxRepository,
      ExportableMailboxRepository exportableMailboxRepository) {
    this.mailboxRepository = mailboxRepository;
    this.exportableMailboxRepository = exportableMailboxRepository;
    this.authSession = authSession;
  }

  private ExportableMailbox convertToExportableMailbox(Mailbox mailbox) {
    List<Email> readEmails = mailbox.getEmailsWithLabel(Label.READ);

    List<Email> unreadEmails = mailbox.getEmailsWithLabel(Label.UNREAD);

    List<ExportableEmail> exportableReadEmails = getExportableEmails(readEmails, true);

    List<ExportableEmail> exportableUnreadEmails = getExportableEmails(unreadEmails, false);

    List<ExportableEmail> allExportableEmails =
        Stream.concat(exportableReadEmails.stream(), exportableUnreadEmails.stream()).toList();

    LocalDateTime exportedDate = LocalDateTime.now();

    return new ExportableMailbox(
        mailbox.getOwner().toString(),
        mailbox.getType().toString(),
        allExportableEmails,
        exportedDate);
  }

  private List<ExportableEmail> getExportableEmails(List<Email> readEmails, boolean isRead) {
    List<ExportableEmail> exportableEmailList = new ArrayList<>();
    for (Email email : readEmails) {
      Map<String, String> headerMap =
          email.getHeaders().stream().collect(Collectors.toMap(Header::name, Header::value));
      ExportableRecipients exportableRecipients =
          new ExportableRecipients(
              email.getRecipientBCC().stream().map(Address::toString).toList(),
              email.getRecipientCC().stream().map(Address::toString).toList(),
              email.getRecipientTo().stream().map(Address::toString).toList());

      ExportableEmail exportableEmail =
          new ExportableEmail(
              email.getSubject().subject(),
              email.getContent(),
              email.getSender().toString(),
              exportableRecipients,
              email.getSendDate().date(),
              isRead,
              headerMap);
      exportableEmailList.add(exportableEmail);
    }
    return exportableEmailList;
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
    exportableMailboxRepository.saveMailbox(exportableMailbox);
  }
}
