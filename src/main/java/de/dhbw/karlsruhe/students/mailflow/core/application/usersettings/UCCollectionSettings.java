package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.ScheduledSendTimeParserUseCase;

/**
 * @author seiferla
 */
public record UCCollectionSettings(ChangeSignatureService changeSignatureService,
    ScheduledSendTimeParserUseCase scheduledSendTimeParserUseCase) {

  public static UCCollectionSettings init(
      AuthSessionUseCase authSession, UserSettingsRepository userSettingsRepository,
      ScheduledSendTimeParserUseCase scheduledSendTimeParserUseCase) {
    return new UCCollectionSettings(
        new ChangeSignatureService(userSettingsRepository, authSession),
        scheduledSendTimeParserUseCase);
  }
}
