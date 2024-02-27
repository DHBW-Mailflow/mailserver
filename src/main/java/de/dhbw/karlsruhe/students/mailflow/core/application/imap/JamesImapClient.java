package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

import java.util.Properties;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Jdk14Logger;
import org.apache.james.imap.api.display.HumanReadableText;
import org.apache.james.imap.api.display.Locales;
import org.apache.james.imap.api.display.Localizer;
import org.apache.james.imap.api.process.ImapProcessor;
import org.apache.james.imap.decode.ImapDecoder;
import org.apache.james.imap.decode.main.DefaultImapDecoder;
import org.apache.james.imap.encode.ACLResponseEncoder;
import org.apache.james.imap.encode.AuthenticateResponseEncoder;
import org.apache.james.imap.encode.CapabilityResponseEncoder;
import org.apache.james.imap.encode.ContinuationResponseEncoder;
import org.apache.james.imap.encode.ESearchResponseEncoder;
import org.apache.james.imap.encode.EnableResponseEncoder;
import org.apache.james.imap.encode.ExistsResponseEncoder;
import org.apache.james.imap.encode.ExpungeResponseEncoder;
import org.apache.james.imap.encode.FetchResponseEncoder;
import org.apache.james.imap.encode.FlagsResponseEncoder;
import org.apache.james.imap.encode.IdResponseEncoder;
import org.apache.james.imap.encode.LSubResponseEncoder;
import org.apache.james.imap.encode.ListResponseEncoder;
import org.apache.james.imap.encode.ListRightsResponseEncoder;
import org.apache.james.imap.encode.MailboxStatusResponseEncoder;
import org.apache.james.imap.encode.MetadataResponseEncoder;
import org.apache.james.imap.encode.MyRightsResponseEncoder;
import org.apache.james.imap.encode.NamespaceResponseEncoder;
import org.apache.james.imap.encode.QuotaResponseEncoder;
import org.apache.james.imap.encode.QuotaRootResponseEncoder;
import org.apache.james.imap.encode.RecentResponseEncoder;
import org.apache.james.imap.encode.SearchResponseEncoder;
import org.apache.james.imap.encode.StatusResponseEncoder;
import org.apache.james.imap.encode.VanishedResponseEncoder;
import org.apache.james.imap.encode.XListResponseEncoder;
import org.apache.james.imap.encode.base.EndImapEncoder;
import org.apache.james.imap.encode.main.DefaultImapEncoderFactory;
import org.apache.james.imap.main.DefaultImapDecoderFactory;
import org.apache.james.imap.processor.main.DefaultImapProcessorFactory;
import org.apache.james.imapserver.netty.IMAPServer;
import org.apache.james.mailbox.MailboxManager;

import de.dhbw.karlsruhe.students.mailflow.App;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.james.JamesMailboxManager;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import reactor.util.annotation.NonNull;

public class JamesImapClient implements ImapClient {

    private Session session;
    private IMAPServer server;

    public JamesImapClient(@NonNull ImapClientConfig config) throws Exception {
        var logger = new Jdk14Logger("JamesImapClient");

        var server = new IMAPServer();
        server.setIP(config.host());
        server.setPort(config.port());
        server.setLog(logger);

        Localizer localizer = new Localizer() {
            @Override
            public String localize(HumanReadableText text, Locales locales) {
                return text.asString();
            }
        };

        server.setImapEncoder(new DefaultImapEncoderFactory.DefaultImapEncoder(Stream.of(
                new MetadataResponseEncoder(),
                new MyRightsResponseEncoder(),
                new ListRightsResponseEncoder(),
                new ListResponseEncoder(),
                new ACLResponseEncoder(),
                new NamespaceResponseEncoder(),
                new StatusResponseEncoder(localizer),
                new RecentResponseEncoder(),
                new ExpungeResponseEncoder(),
                new ExistsResponseEncoder(),
                new IdResponseEncoder(),
                new MailboxStatusResponseEncoder(),
                new SearchResponseEncoder(),
                new LSubResponseEncoder(),
                new XListResponseEncoder(),
                new FlagsResponseEncoder(),
                new CapabilityResponseEncoder(),
                new EnableResponseEncoder(),
                new ContinuationResponseEncoder(localizer),
                new AuthenticateResponseEncoder(),
                new ESearchResponseEncoder(),
                new VanishedResponseEncoder(),
                new QuotaResponseEncoder(),
                new QuotaRootResponseEncoder()),
                new EndImapEncoder()));
        server.setImapDecoder(DefaultImapDecoderFactory.createDecoder());

        var mailboxManager = new JamesMailboxManager(logger);

        server.setImapProcessor(DefaultImapProcessorFactory.createDefaultProcessor(
                mailboxManager, null, null, null, null, null));

        server.start();
        this.server = server;
    }

    public void stop() {
        this.server.stop();
    }
}