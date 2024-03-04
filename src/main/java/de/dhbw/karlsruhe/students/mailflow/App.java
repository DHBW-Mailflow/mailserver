package de.dhbw.karlsruhe.students.mailflow;

import java.io.IOException;
import java.net.ServerSocket;

import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapListener;
import de.dhbw.karlsruhe.students.mailflow.core.application.imap.ImapListenerConfig;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.JamesImapListener;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        var config = new ImapListenerConfig("127.0.0.1", App.getFreePort());

        ImapListener client = new JamesImapListener();
        client.configure(config);

        client.listen();
    }

    /**
     * @throws IOException
     */
    public static int getFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            var port = socket.getLocalPort();

            if (port != -1) {
                return port;
            }
        } catch (IOException e) {
        }

        throw new IOException("unable to find free port");
    }
}
