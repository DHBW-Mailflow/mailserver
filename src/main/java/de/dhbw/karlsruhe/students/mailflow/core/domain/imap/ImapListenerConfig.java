package de.dhbw.karlsruhe.students.mailflow.core.domain.imap;

import java.io.IOException;
import java.net.ServerSocket;

public record ImapListenerConfig(String host, int port) {

  public static ImapListenerConfig createLocalConfig() throws IOException {
      return new ImapListenerConfig("127.0.0.1", ImapListenerConfig.getFreePort());
  }

  /**
   * @throws IOException if there occurred an issue during the port allocation
   */
  private static int getFreePort() throws IOException {
    try (ServerSocket socket = new ServerSocket(0)) {
      var port = socket.getLocalPort();

      if (port != -1) {
        return port;
      }
    } catch (IOException e) {
      // no-op
    }

    throw new IOException("unable to find free port");
  }

}
