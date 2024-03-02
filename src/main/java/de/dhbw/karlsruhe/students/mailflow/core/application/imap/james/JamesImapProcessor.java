package de.dhbw.karlsruhe.students.mailflow.core.application.imap.james;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.james.core.Username;
import org.apache.james.imap.api.ImapConfiguration;
import org.apache.james.imap.api.ImapMessage;
import org.apache.james.imap.api.display.HumanReadableText;
import org.apache.james.imap.api.message.Capability;
import org.apache.james.imap.api.message.request.ImapRequest;
import org.apache.james.imap.api.message.response.StatusResponseFactory;
import org.apache.james.imap.api.process.ImapProcessor;
import org.apache.james.imap.api.process.ImapSession;
import org.apache.james.imap.message.request.AuthenticateRequest;
import org.apache.james.imap.message.request.CapabilityRequest;
import org.apache.james.imap.message.response.AuthenticateResponse;
import org.apache.james.imap.message.response.CapabilityResponse;
import org.apache.james.imap.message.response.UnpooledStatusResponseFactory;
import org.apache.james.mailbox.MailboxManager;
import org.apache.james.mailbox.MailboxSession;
import org.apache.james.mailbox.MailboxSession.SessionId;
import org.apache.james.mailbox.MailboxSession.SessionType;
import org.apache.james.mailbox.exception.MailboxException;
import de.dhbw.karlsruhe.students.mailflow.core.util.RandomString;

public class JamesImapProcessor implements ImapProcessor {

    private StatusResponseFactory statusResponseFactory;
    private MailboxManager mailboxManager;

    public JamesImapProcessor() {
        this.statusResponseFactory = new UnpooledStatusResponseFactory();
        this.mailboxManager = new JamesMailboxManager();
    }

    @Override
    public void configure(ImapConfiguration imapConfiguration) {}

    public void process(ImapMessage message, Responder responder, ImapSession session) {
        if (message instanceof CapabilityRequest) {
            this.processCapabilityRequest((CapabilityRequest) message, responder, session);
        } else if (message instanceof AuthenticateRequest) {
            this.processAuthenticateRequest((AuthenticateRequest) message, responder, session);
        } else {
            System.out.println(message);
            // this.processReactive(message, responder, session).block();
        }
    }

    private void processCapabilityRequest(CapabilityRequest message, Responder responder,
            ImapSession session) {
        // TODO: Refactor available auth methods away
        responder.respond(new CapabilityResponse(Set.of(Capability.of("IMAP4"),
                Capability.of("IMAP4rev1"), Capability.of("AUTH=PLAIN"))));
        responder.respond(statusResponseFactory.taggedOk(message.getTag(), message.getCommand(),
                HumanReadableText.OK));
    }

    private void processAuthenticateRequest(AuthenticateRequest message, Responder responder,
            ImapSession session) {
        // TODO: See 6.2.2. AUTHENTICATE Command (RFC 3501)

        // TODO: Refactor to PLAIN-Authentication Strategy pattern
        System.out.println(message.toString());
        responder.respond(new AuthenticateResponse());
        responder.flush();
        session.pushLineHandler((requestSession, data) -> {
            try {
                doPlainAuth(extractInitialClientResponse(data), requestSession, message, responder);
            } catch (MailboxException e) {
                e.printStackTrace();
            }
            // remove the handler now
            requestSession.popLineHandler();
            responder.flush();
        });
    }

    private static String extractInitialClientResponse(byte[] data) {
        // cut of the CRLF
        return new String(data, 0, data.length - 2, StandardCharsets.US_ASCII);
    }

    /**
     * Parse the initialClientResponse and do a PLAIN AUTH with it
     *
     * @throws MailboxException
     */
    protected void doPlainAuth(String initialClientResponse, ImapSession session,
            ImapRequest request, Responder responder) throws MailboxException {

        String userpass = new String(Base64.getDecoder().decode(initialClientResponse));
        List<String> tokens = Arrays.stream(userpass.split("\0")).filter(token -> !token.isBlank())
                .collect(Collectors.toList());

        if (tokens.size() != 2) {
            throw new MailboxException("invalid login attempt");
        }

        var username = Username.of(tokens.get(0));

        // TODO: Check if username already exists, then check password or register otherwise

        var sessionId = new Random().nextLong();

        session.setMailboxSession(new MailboxSession(SessionId.of(sessionId), username,
                Optional.of(username), List.of(Locale.ENGLISH), '/', SessionType.User));

        session.authenticated();
        session.stopDetectingCommandInjection();

        // send completed
        responder.respond(statusResponseFactory.taggedOk(request.getTag(), request.getCommand(),
                HumanReadableText.COMPLETED));
    }

    public MailboxManager getMailboxManager() {
        return mailboxManager;
    }
}
