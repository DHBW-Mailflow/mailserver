package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.RemoveSettingsException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.SaveSettingsException;

public interface ChangeSignatureUseCase {
  void updateSignature(String newSignature) throws LoadSettingsException, SaveSettingsException;

  void resetSignature() throws LoadSettingsException, SaveSettingsException;

  String getSignature() throws LoadSettingsException, SaveSettingsException;
}
