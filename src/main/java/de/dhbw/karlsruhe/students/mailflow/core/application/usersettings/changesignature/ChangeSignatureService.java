package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;

public class ChangeSignatureService implements ChangeSignatureUseCase {

  private final UserSettingsRepository userSettingsRepository;
  private final AuthSessionUseCase authSession;

  public ChangeSignatureService(UserSettingsRepository userSettingsRepository,
      AuthSessionUseCase authSession) {
    this.userSettingsRepository = userSettingsRepository;
    this.authSession = authSession;
  }

  @Override
  public void updateSignature(String newSignature) {


  }

  @Override
  public void removeSignature() {

  }
}
