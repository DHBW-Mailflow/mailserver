package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

/**
 * @author Jonas-Karl
 */
public interface ListenerService {

  void listen();

  void stop();

  int getPort();

  String getHost();
}
