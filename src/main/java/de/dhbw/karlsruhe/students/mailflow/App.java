package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ListenerService;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.LocalListenerService;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.james.JamesImapListener;
import java.net.InetSocketAddress;
import org.apache.james.metrics.api.MetricFactory;
import org.apache.james.metrics.logger.DefaultMetricFactory;
import org.apache.james.protocols.api.handler.ProtocolHandlerChain;
import org.apache.james.protocols.netty.NettyServer;
import org.apache.james.protocols.smtp.SMTPConfiguration;
import org.apache.james.protocols.smtp.SMTPConfigurationImpl;
import org.apache.james.protocols.smtp.SMTPProtocol;
import org.apache.james.protocols.smtp.SMTPProtocolHandlerChain;

/**
 * Hello world!
 *
 * @author jens1o, Jonas-Karl, seiferla
 */
public class App {
  public static void main(String[] args) throws Exception {
    int smtpPort = 50200;
    String smtpHost = "localhost";
    SMTPConfiguration smtpConfiguration = new SMTPConfigurationImpl();
    MetricFactory metricFactory = new DefaultMetricFactory();
    ProtocolHandlerChain chain = new SMTPProtocolHandlerChain(metricFactory);

    NettyServer server =
        new NettyServer.Factory().protocol(new SMTPProtocol(chain, smtpConfiguration)).build();
    server.setListenAddresses(new InetSocketAddress(smtpHost, smtpPort));
    server.bind();
    System.out.printf("Server started with addresses: %s", server.getListenAddresses().toString());

    ListenerService listenerService = new LocalListenerService(new JamesImapListener());
    listenerService.listen();
  }
}
