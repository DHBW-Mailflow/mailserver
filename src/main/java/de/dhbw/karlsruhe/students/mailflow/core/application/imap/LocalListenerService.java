package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

import de.dhbw.karlsruhe.students.mailflow.core.domain.imap.ImapListenerConfig;
import java.io.IOException;

/**
 * @author Jonas-Karl
 */
public class LocalListenerService implements ListenerService {
  private final ImapListener imapListener;
  private final ImapListenerConfig config;

  public LocalListenerService(ImapListener imapListener) throws IOException {
    this.imapListener = imapListener;
    this.config = ImapListenerConfig.createLocalConfig();
    this.imapListener.configure(config);
  }

  @Override
  public void listen() {
    imapListener.listen();
  }

  @Override
  public void stop() {
    imapListener.stop();
  }

  @Override
  public int getPort() {
    return config.port();
  }

  @Override
  public String getHost() {
    return config.host();
  }
}
