package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureService;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;

public record UCCollectionSettings(ChangeSignatureUseCase changeSignatureUseCase) {

  public static UCCollectionSettings init(
      AuthSessionUseCase authSession, UserSettingsRepository userSettingsRepository) {
    return new UCCollectionSettings(
        new ChangeSignatureService(userSettingsRepository, authSession));
  }
}
