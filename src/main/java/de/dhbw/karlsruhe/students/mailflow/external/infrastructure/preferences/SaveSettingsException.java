package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

/**
 * @author seiferla
 */
public class SaveSettingsException extends Exception {
  public SaveSettingsException(String couldNotSaveUserSettings, Exception e) {
    super(couldNotSaveUserSettings, e);
  }
}