package de.dhbw.karlsruhe.students.mailflow;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ListenerService;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.LocalListenerService;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.james.JamesImapListener;
import jakarta.mail.Session;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImapHandshakeTest {
  private ListenerService server;

  @BeforeEach
  void setUp() throws IOException {
    server = new LocalListenerService(new JamesImapListener());
    server.listen();
  }

  @AfterEach
  void tearDown() {
    server.stop();
  }

  @Test
  void testJamesImapSurvivesHandshake() throws IOException {

    // Arrange
    var clientConfig = new Properties();
    clientConfig.put("mail.host", server.getHost());
    clientConfig.put("mail.port", String.valueOf(server.getPort()));
    clientConfig.put("mail.debug", "true");
    clientConfig.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");

    var session = Session.getDefaultInstance(clientConfig);

    // Assert
    assertDoesNotThrow(
        () -> {
          // Act
          var store = session.getStore("imap");

          store.connect(server.getHost(), server.getPort(), "admin", "admin");
        });

  }


}
