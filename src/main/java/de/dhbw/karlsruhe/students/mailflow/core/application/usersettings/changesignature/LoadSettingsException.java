package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

public class LoadSettingsException extends Exception {

  public LoadSettingsException(String message, Exception e) {
    super(message, e);
  }
  public LoadSettingsException(String message) {
    super(message);
  }
}
