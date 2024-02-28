package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

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

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.james.JamesFileSystem;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.james.JamesImapProcessor;
import jakarta.mail.Session;
import reactor.util.annotation.NonNull;

public class JamesImapClient implements ImapClient {

    private AbstractConfigurableAsyncServer server;

    public JamesImapClient(@NonNull ImapClientConfig imapClientConfig) throws Exception {
        var server = new IMAPServerFactory(new JamesFileSystem(), () -> DefaultImapDecoderFactory.createDecoder(),
                () -> DefaultImapEncoderFactory.createDefaultEncoder(new Localizer() {
                    @Override
                    public String localize(HumanReadableText text, Locales locales) {
                        return text.toString();
                    }
                }, false), () -> new JamesImapProcessor(), new DefaultMetricFactory(), new NoopGaugeRegistry());

        var config = new BaseHierarchicalConfiguration();
        config.setProperty("imapserver.[@enabled]", true);
        config.setProperty("imapserver.bind", imapClientConfig.host() + ":" + imapClientConfig.port());

        server.configure(config);

        server.init();
        this.server = server.getServers().get(0);
    }

    public void stop() {
        this.server.stop();
    }
}