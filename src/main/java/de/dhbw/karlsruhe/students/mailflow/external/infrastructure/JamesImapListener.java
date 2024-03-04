package de.dhbw.karlsruhe.students.mailflow.external.infrastructure;

import org.apache.commons.configuration2.BaseHierarchicalConfiguration;
import org.apache.james.imap.api.display.HumanReadableText;
import org.apache.james.imap.api.display.Locales;
import org.apache.james.imap.api.display.Localizer;
import org.apache.james.imap.encode.main.DefaultImapEncoderFactory;
import org.apache.james.imap.main.DefaultImapDecoderFactory;
import org.apache.james.imapserver.netty.IMAPServerFactory;
import org.apache.james.metrics.api.NoopGaugeRegistry;
import org.apache.james.metrics.logger.DefaultMetricFactory;
import org.apache.james.protocols.lib.netty.AbstractConfigurableAsyncServer;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapListener;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapListenerConfig;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.james.JamesImapProcessor;

public class JamesImapListener implements ImapListener {

    private AbstractConfigurableAsyncServer server;
    private ImapListenerConfig config;

    public void listen() throws Exception {
        var server = new IMAPServerFactory(null, () -> DefaultImapDecoderFactory.createDecoder(),
                () -> DefaultImapEncoderFactory.createDefaultEncoder(new Localizer() {
                    @Override
                    public String localize(HumanReadableText text, Locales locales) {
                        return text.toString();
                    }
                }, false), () -> new JamesImapProcessor(), new DefaultMetricFactory(),
                new NoopGaugeRegistry());

        var config = new BaseHierarchicalConfiguration();
        config.setProperty("imapserver.[@enabled]", true);
        config.setProperty("imapserver.bind", this.config.host() + ":" + this.config.port());

        server.configure(config);

        server.init();
        this.server = server.getServers().get(0);
    }

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
