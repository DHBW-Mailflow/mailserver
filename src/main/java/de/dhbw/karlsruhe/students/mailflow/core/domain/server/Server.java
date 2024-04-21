package de.dhbw.karlsruhe.students.mailflow.core.domain.server;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;

public interface Server {
  void start();

  void stop();
}
