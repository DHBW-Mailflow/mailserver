package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;

public class ChangeSignatureService implements ChangeSignatureUseCase {

  private final UserSettingsRepository userSettingsRepository;

  public ChangeSignatureService(UserSettingsRepository userSettingsRepository) {
    this.userSettingsRepository = userSettingsRepository;
  }

  @Override
  public void updateSignature(String newSignature) {


  }

  @Override
  public void removeSignature() {

  }
}
