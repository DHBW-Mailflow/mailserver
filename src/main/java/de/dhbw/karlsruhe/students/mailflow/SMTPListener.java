package de.dhbw.karlsruhe.students.mailflow;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.james.metrics.api.MetricFactory;
import org.apache.james.metrics.logger.DefaultMetricFactory;
import org.apache.james.protocols.api.handler.CommandDispatcher;
import org.apache.james.protocols.api.handler.CommandHandler;
import org.apache.james.protocols.api.handler.ProtocolHandler;
import org.apache.james.protocols.api.handler.ProtocolHandlerChain;
import org.apache.james.protocols.api.handler.WiringException;
import org.apache.james.protocols.netty.NettyServer;
import org.apache.james.protocols.smtp.SMTPConfiguration;
import org.apache.james.protocols.smtp.SMTPConfigurationImpl;
import org.apache.james.protocols.smtp.SMTPProtocol;
import org.apache.james.protocols.smtp.SMTPProtocolHandlerChain;
import org.apache.james.protocols.smtp.core.DataCmdHandler;
import org.apache.james.protocols.smtp.core.DataLineFilter;
import org.apache.james.protocols.smtp.core.ExpnCmdHandler;
import org.apache.james.protocols.smtp.core.HeloCmdHandler;
import org.apache.james.protocols.smtp.core.HelpCmdHandler;
import org.apache.james.protocols.smtp.core.MailCmdHandler;
import org.apache.james.protocols.smtp.core.NoopCmdHandler;
import org.apache.james.protocols.smtp.core.QuitCmdHandler;
import org.apache.james.protocols.smtp.core.RcptCmdHandler;
import org.apache.james.protocols.smtp.core.RsetCmdHandler;
import org.apache.james.protocols.smtp.core.UnknownCmdHandler;
import org.apache.james.protocols.smtp.core.VrfyCmdHandler;
import org.apache.james.protocols.smtp.core.esmtp.EhloCmdHandler;
import org.apache.james.protocols.smtp.core.esmtp.StartTlsCmdHandler;
import org.apache.james.smtpserver.DataLineJamesMessageHookHandler;
import org.apache.james.smtpserver.ExtendedSMTPSession;

public class SMTPListener {

  private final NettyServer server;

  public SMTPListener(String host, int port) {
    MetricFactory metricFactory = new DefaultMetricFactory();
    ProtocolHandlerChain chain = new SMTPProtocolHandlerChain(metricFactory);
    CommandDispatcher<ExtendedSMTPSession> commandDispatcher =
        chain.getHandlers(CommandDispatcher.class).get(0);
    List<ProtocolHandler> commandHandlers = new ArrayList<>();
    //   defaultHandlers.add(new CommandDispatcher<SMTPSession>());
    commandHandlers.add(new ExpnCmdHandler());
    commandHandlers.add(new EhloCmdHandler(metricFactory));
    commandHandlers.add(new HeloCmdHandler(metricFactory));
    commandHandlers.add(new HelpCmdHandler());
    commandHandlers.add(new MailCmdHandler(metricFactory));
    commandHandlers.add(new NoopCmdHandler());
    commandHandlers.add(new QuitCmdHandler(metricFactory));
    commandHandlers.add(new RcptCmdHandler(metricFactory));
    commandHandlers.add(new RsetCmdHandler());
    commandHandlers.add(new VrfyCmdHandler());
    DataCmdHandler dataCmdHandler = new DataCmdHandler(metricFactory);
    commandHandlers.add(dataCmdHandler);
    commandHandlers.add(new StartTlsCmdHandler());
    commandHandlers.add(new UnknownCmdHandler(metricFactory));

    /*          List.of(
    new HeloCmdHandler(metricFactory),
    new MailCmdHandler(metricFactory),
    new RcptCmdHandler(metricFactory),
    new DataCmdHandler(metricFactory))*/

    /*    List<ProtocolHandler> defaultHandlers = new ArrayList<>();
    defaultHandlers.add(new MailSizeEsmtpExtension());
     defaultHandlers.add(new WelcomeMessageHandler());
     defaultHandlers.add(new PostmasterAbuseRcptHook()); //RcptHook
    defaultHandlers.add(new ReceivedDataLineFilter());
     defaultHandlers.add(new DataLineMessageHookHandler());
      defaultHandlers.add(new CommandHandlerResultLogger());*/
    // DataLineMessageHookHandler

    try {
      commandDispatcher.wireExtensions(CommandHandler.class, commandHandlers);
      dataCmdHandler.wireExtensions(
          DataLineFilter.class, List.of(new DataLineJamesMessageHookHandler()));

      /*for (ProtocolHandler handler : defaultHandlers) {
      commandDispatcher.wireExtensions(
          handler.getClass().getSuperclass(),
          List.of(handler)
      );*/

    } catch (WiringException e) {
      throw new RuntimeException(e);
    }
    SMTPConfiguration smtpConfiguration = new SMTPConfigurationImpl();

    server = new NettyServer.Factory().protocol(new SMTPProtocol(chain, smtpConfiguration)).build();
    server.setListenAddresses(new InetSocketAddress(host, port));
  }

  public void listen() throws Exception {
    this.server.bind();
  }

  public List<InetSocketAddress> getListenAddresses() {
    return server.getListenAddresses();
  }
}
