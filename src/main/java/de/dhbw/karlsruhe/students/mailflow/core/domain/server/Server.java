package de.dhbw.karlsruhe.students.mailflow.core.domain.server;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;

public interface Server {
  void start() throws MailboxSavingException, MailboxLoadingException;

  void stop();
}
