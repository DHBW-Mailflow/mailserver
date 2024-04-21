package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.RemoveSettingsException;
import java.io.FileNotFoundException;

public interface ChangeSignatureUseCase {
  void updateSignature(String newSignature) throws LoadSettingsException;

  void resetSignature() throws RemoveSettingsException;

  String getSignature() throws LoadSettingsException;
}
