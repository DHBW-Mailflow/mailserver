package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changepassword.ChangePasswordUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.ScheduledSendTimeParserUseCase;

/**
 * @author seiferla
 */
public record UCCollectionSettings(
    AuthSessionUseCase authSessionUseCase,
    ChangeSignatureService changeSignatureService,
    ScheduledSendTimeParserUseCase scheduledSendTimeParserUseCase,
    ChangePasswordUseCase changePasswordUseCase) {

  public static UCCollectionSettings init(
      AuthSessionUseCase authSessionUseCase, UserSettingsRepository userSettingsRepository,
      ScheduledSendTimeParserUseCase scheduledSendTimeParserUseCase,
      ChangePasswordUseCase changePasswordUseCase) {
    return new UCCollectionSettings(
        authSessionUseCase,
        new ChangeSignatureService(userSettingsRepository, authSessionUseCase),
        scheduledSendTimeParserUseCase, changePasswordUseCase);
  }
}
