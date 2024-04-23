package de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions;

/**
 * @author seiferla
 */
public class SaveSettingsException extends Exception {
  public SaveSettingsException(String couldNotSaveUserSettings, Exception e) {
    super(couldNotSaveUserSettings, e);
  }
}
