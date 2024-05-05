package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changepassword.ChangePasswordService;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changepassword.ChangePasswordUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureService;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export.ExportUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.export.MailboxExportService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.ExportableMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.ScheduledSendTimeParserService;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.ScheduledSendTimeParserUseCase;
import java.util.Objects;

/**
 * @author seiferla
 */
public final class UCCollectionSettings {
  private final ChangeSignatureService changeSignatureService;
  private final ScheduledSendTimeParserUseCase scheduledSendTimeParserUseCase;
  private final ChangePasswordUseCase changePasswordUseCase;
  private final ExportUseCase exportUseCase;

  /** */
  public UCCollectionSettings(
      ChangeSignatureService changeSignatureService,
      ScheduledSendTimeParserUseCase scheduledSendTimeParserUseCase,
      ChangePasswordUseCase changePasswordUseCase,
      ExportUseCase exportUseCase) {
    this.changeSignatureService = changeSignatureService;
    this.scheduledSendTimeParserUseCase = scheduledSendTimeParserUseCase;
    this.changePasswordUseCase = changePasswordUseCase;
    this.exportUseCase = exportUseCase;
  }

  public static UCCollectionSettings init(
      AuthSessionUseCase authSessionUseCase,
      UserSettingsRepository userSettingsRepository,
      UserRepository userRepository,
      MailboxRepository mailboxRepository,
      ExportableMailboxRepository exportableMailboxRepository) {
    return new UCCollectionSettings(
        new ChangeSignatureService(userSettingsRepository, authSessionUseCase),
        new ScheduledSendTimeParserService(),
        new ChangePasswordService(userRepository, authSessionUseCase),
        new MailboxExportService(authSessionUseCase, mailboxRepository, exportableMailboxRepository));
  }


  public ChangeSignatureService changeSignatureService() {
    return changeSignatureService;
  }

  public ScheduledSendTimeParserUseCase scheduledSendTimeParserUseCase() {
    return scheduledSendTimeParserUseCase;
  }

  public ChangePasswordUseCase changePasswordUseCase() {
    return changePasswordUseCase;
  }

  public ExportUseCase exportUseCase() {
    return exportUseCase;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (UCCollectionSettings) obj;
    return Objects.equals(this.changeSignatureService, that.changeSignatureService)
        && Objects.equals(this.scheduledSendTimeParserUseCase, that.scheduledSendTimeParserUseCase)
        && Objects.equals(this.changePasswordUseCase, that.changePasswordUseCase)
        && Objects.equals(this.exportUseCase, that.exportUseCase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        changeSignatureService,
        scheduledSendTimeParserUseCase,
        changePasswordUseCase,
        exportUseCase);
  }

  @Override
  public String toString() {
    return "UCCollectionSettings["
        + "changeSignatureService="
        + changeSignatureService
        + ", "
        + "scheduledSendTimeParserUseCase="
        + scheduledSendTimeParserUseCase
        + ", "
        + "changePasswordUseCase="
        + changePasswordUseCase
        + ", "
        + "exportUseCase="
        + exportUseCase
        + ']';
  }
}
