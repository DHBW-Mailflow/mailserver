package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import java.io.FileNotFoundException;

public class RemoveSettingsException extends Exception {
  public RemoveSettingsException(String failedToRemoveUserSettings, FileNotFoundException e) {
    super(failedToRemoveUserSettings, e);
  }
}
