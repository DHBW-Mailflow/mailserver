package de.dhbw.karlsruhe.students.mailflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 * @author jens1o, Jonas-Karl, seiferla
 */
public class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {

    logger.info("Starting SMTP...");
    int smtpPort = 50200;
    String smtpHost = "localhost";
    SMTPListener smtpListener = new SMTPListener(smtpHost, smtpPort);
    //   DataCmdHandler
    smtpListener.listen();
    logger.info(
        "SMTP Server started with addresses: %s"
            .formatted(smtpListener.getListenAddresses().toString()));

    // ListenerService listenerService = new LocalListenerService(new JamesImapListener());
    //  listenerService.listen();
  }
}
