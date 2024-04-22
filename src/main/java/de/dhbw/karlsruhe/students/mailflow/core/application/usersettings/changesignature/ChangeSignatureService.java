package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.SaveSettingsException;

/**
 * @author seiferla
 */
public class ChangeSignatureService implements ChangeSignatureUseCase {

  private final UserSettingsRepository userSettingsRepository;
  private final AuthSessionUseCase authSession;

  public ChangeSignatureService(
      UserSettingsRepository userSettingsRepository, AuthSessionUseCase authSession) {
    this.userSettingsRepository = userSettingsRepository;
    this.authSession = authSession;
  }

  @Override
  public void updateSignature(String newSignature)
      throws LoadSettingsException, SaveSettingsException {

    UserSettings userSettings =
        new SettingsBuilder()
            .withSignature(newSignature)
            .withAddress(authSession.getSessionUserAddress())
            .build();
    userSettingsRepository.updateUserSettings(userSettings);
  }

  @Override
  public void resetSignature() throws LoadSettingsException, SaveSettingsException {
    UserSettings currentUserSettings = userSettingsRepository.getSettings(authSession.getSessionUserAddress());
    UserSettings updatedUserSettings = new SettingsBuilder(currentUserSettings)
        .withSignature("")
            .build();
    userSettingsRepository.updateUserSettings(updatedUserSettings);
  }

  @Override
  public String getSignature() throws LoadSettingsException, SaveSettingsException {
    return userSettingsRepository.getSettings(authSession.getSessionUserAddress()).signature();
  }
}
