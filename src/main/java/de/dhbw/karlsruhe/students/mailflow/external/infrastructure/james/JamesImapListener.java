package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.james;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapListener;
import de.dhbw.karlsruhe.students.mailflow.core.domain.imap.ImapListenerConfig;
import de.dhbw.karlsruhe.students.mailflow.core.domain.imap.ImapListenerException;
import org.apache.commons.configuration2.BaseHierarchicalConfiguration;
import org.apache.james.imap.encode.main.DefaultImapEncoderFactory;
import org.apache.james.imap.main.DefaultImapDecoderFactory;
import org.apache.james.imapserver.netty.IMAPServerFactory;
import org.apache.james.metrics.api.NoopGaugeRegistry;
import org.apache.james.metrics.logger.DefaultMetricFactory;
import org.apache.james.protocols.lib.netty.AbstractConfigurableAsyncServer;

public class JamesImapListener implements ImapListener {

  private AbstractConfigurableAsyncServer server;
  private ImapListenerConfig config;

  @Override
  public void listen() throws ImapListenerException {
    var jamesServer =
        new IMAPServerFactory(
            null,
            DefaultImapDecoderFactory::createDecoder,
            () ->
                DefaultImapEncoderFactory.createDefaultEncoder(
                    (text, locales) -> text.toString(), false),
            JamesImapProcessor::new,
            new DefaultMetricFactory(),
            new NoopGaugeRegistry());

    var jamesConfig = new BaseHierarchicalConfiguration();
    jamesConfig.setProperty("imapserver.[@enabled]", true);
    jamesConfig.setProperty("imapserver.bind", this.config.host() + ":" + this.config.port());

    jamesServer.configure(jamesConfig);

    try {
      jamesServer.init();
    } catch (Exception e) {
      throw new ImapListenerException("cannot initialize imap server", e);
    }

    this.server = jamesServer.getServers().get(0);
  }

  @Override
  public void stop() {
    if (this.server == null) {
      throw new IllegalStateException("server must be started prior to terminating");
    }

    this.server.stop();
  }

  @Override
  public void configure(ImapListenerConfig config) {
    this.config = config;
  }
}
