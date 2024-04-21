package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.RemoveSettingsException;
import java.io.FileNotFoundException;

public class ChangeSignatureService implements ChangeSignatureUseCase {

  private final UserSettingsRepository userSettingsRepository;
  private final AuthSessionUseCase authSession;

  public ChangeSignatureService(
      UserSettingsRepository userSettingsRepository, AuthSessionUseCase authSession) {
    this.userSettingsRepository = userSettingsRepository;
    this.authSession = authSession;
  }

  @Override
  public void updateSignature(String newSignature) throws LoadSettingsException {

    String signature = new SignatureBuilder().signature(newSignature).build();
    UserSettings userSettings = new UserSettings(signature);
    userSettingsRepository.updateUserSettings(authSession.getSessionUserAddress(), userSettings);
  }

  @Override
  public void resetSignature() throws RemoveSettingsException {
    userSettingsRepository.removeUserSettings(authSession.getSessionUserAddress());
  }

  @Override
  public String getSignature() throws LoadSettingsException {
    return userSettingsRepository.getSiginature(authSession.getSessionUserAddress());
  }


}
