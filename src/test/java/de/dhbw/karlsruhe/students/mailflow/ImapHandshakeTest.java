package de.dhbw.karlsruhe.students.mailflow;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapClientConfig;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.JamesImapClient;
import jakarta.mail.Session;

public class ImapHandshakeTest {
    @Test
    public void testJamesImapSurvivesHandshake() throws Exception {
        // Arrange
        var config = new ImapClientConfig("127.0.0.1", App.getFreePort());
        var server = new JamesImapClient(config);

        var clientConfig = new Properties();
        clientConfig.put("mail.user", "admin");
        clientConfig.put("mail.host", config.host());
        clientConfig.put("mail.port", String.valueOf(config.port()));
        clientConfig.put("mail.debug", "true");
        clientConfig.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");

        var session = Session.getDefaultInstance(clientConfig);

        // Act
        var store = session.getStore("imap");
        store.connect("127.0.0.1", config.port(), "admin", "admin");
        store.getDefaultFolder();

        server.stop();
    }
}
