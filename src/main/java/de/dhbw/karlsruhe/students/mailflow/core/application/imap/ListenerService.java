package de.dhbw.karlsruhe.students.mailflow.core.application.imap;


public interface ListenerService {

  void listen();

  void stop();

  int getPort();

  String getHost();
}
