package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import java.io.IOException;

public class SaveSettingsException extends Exception {
  public SaveSettingsException(String couldNotSaveUserSettings, Exception e) {
    super(couldNotSaveUserSettings, e);
  }
}
