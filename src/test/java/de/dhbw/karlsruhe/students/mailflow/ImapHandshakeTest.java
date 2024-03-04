package de.dhbw.karlsruhe.students.mailflow;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapListenerConfig;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.JamesImapListener;
import jakarta.mail.Session;

public class ImapHandshakeTest {
    @Test
    public void testJamesImapSurvivesHandshake() throws Exception {
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

        // Act
        var store = session.getStore("imap");
        store.connect(config.host(), config.port(), "admin", "admin");

        server.stop();
    }
}
