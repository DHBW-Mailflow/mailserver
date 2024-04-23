package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;

public interface ChangeSignatureUseCase {
  void updateSignature(String newSignature) throws LoadSettingsException, SaveSettingsException;

  void resetSignature() throws LoadSettingsException, SaveSettingsException;

  String getSignature() throws LoadSettingsException, SaveSettingsException;
}
