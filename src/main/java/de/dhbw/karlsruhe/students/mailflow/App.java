package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ListenerService;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.LocalListenerService;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.james.JamesImapListener;

/** Hello world! */
public class App {
  public static void main(String[] args) throws Exception {
    ListenerService listenerService = new LocalListenerService(new JamesImapListener());
    listenerService.listen();
  }
}
