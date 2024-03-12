package de.dhbw.karlsruhe.students.mailflow;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapListenerConfig;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.JamesImapListener;
import jakarta.mail.Session;

class ImapHandshakeTest {
    @Test
    void testJamesImapSurvivesHandshake() throws IOException {
        // Arrange
        var config = new ImapListenerConfig("127.0.0.1", App.getFreePort());
        var server = new JamesImapListener();
        server.configure(config);
        server.listen();

        var clientConfig = new Properties();
        clientConfig.put("mail.host", config.host());
        clientConfig.put("mail.port", String.valueOf(config.port()));
        clientConfig.put("mail.debug", "true");
        clientConfig.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");

        var session = Session.getDefaultInstance(clientConfig);

        // Assert
        assertDoesNotThrow(() -> {
            // Act
            var store = session.getStore("imap");

            store.connect(config.host(), config.port(), "admin", "admin");
        });

        // Arrange
        server.stop();
    }
}
