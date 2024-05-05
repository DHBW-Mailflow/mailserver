package de.dhbw.karlsruhe.students.mailflow.core.domain.imap;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

/**
 * @author jens1o, Jonas-Karl
 */
public final class ImapListenerConfig {
  private final String host;
  private final int port;

  /** */
  public ImapListenerConfig(String host, int port) {
    this.host = host;
    this.port = port;
  }

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

  public String host() {
    return host;
  }

  public int port() {
    return port;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (ImapListenerConfig) obj;
    return Objects.equals(this.host, that.host) && this.port == that.port;
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, port);
  }

  @Override
  public String toString() {
    return "ImapListenerConfig[" + "host=" + host + ", " + "port=" + port + ']';
  }
}
