package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.SaveSettingsException;

public interface ChangeSignatureUseCase {
  void updateSignature(String newSignature) throws LoadSettingsException, SaveSettingsException;

  void resetSignature() throws LoadSettingsException, SaveSettingsException;

  String getSignature() throws LoadSettingsException, SaveSettingsException;
}
