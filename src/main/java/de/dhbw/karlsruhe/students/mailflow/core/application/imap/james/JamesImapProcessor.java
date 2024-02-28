package de.dhbw.karlsruhe.students.mailflow.core.application.imap.james;

import java.util.Set;

import org.apache.james.imap.api.ImapConfiguration;
import org.apache.james.imap.api.ImapMessage;
import org.apache.james.imap.api.display.HumanReadableText;
import org.apache.james.imap.api.message.Capability;
import org.apache.james.imap.api.message.response.StatusResponseFactory;
import org.apache.james.imap.api.process.ImapProcessor;
import org.apache.james.imap.api.process.ImapSession;
import org.apache.james.imap.message.request.AuthenticateRequest;
import org.apache.james.imap.message.request.CapabilityRequest;
import org.apache.james.imap.message.response.CapabilityResponse;
import org.apache.james.imap.message.response.UnpooledStatusResponseFactory;

public class JamesImapProcessor implements ImapProcessor {

    private StatusResponseFactory statusResponseFactory;

    public JamesImapProcessor() {
        this.statusResponseFactory = new UnpooledStatusResponseFactory();
    }

    @Override
    public void configure(ImapConfiguration imapConfiguration) {
    }

    public void process(ImapMessage message, Responder responder, ImapSession session) {
        if (message instanceof CapabilityRequest) {
            this.processCapabilityRequest((CapabilityRequest) message, responder, session);
        } else if (message instanceof AuthenticateRequest) {
            this.processAuthenticateRequest((AuthenticateRequest) message, responder, session);
        } else {
            this.processReactive(message, responder, session).block();
        }
    }

    private void processCapabilityRequest(CapabilityRequest message, Responder responder, ImapSession session) {
        responder.respond(new CapabilityResponse(
                Set.of(Capability.of("IMAP4"), Capability.of("IMAP4rev1"),
                        Capability.of("AUTH=PLAIN"))));
        responder.respond(
                statusResponseFactory.taggedOk(message.getTag(), message.getCommand(), HumanReadableText.OK));
    }

    private void processAuthenticateRequest(AuthenticateRequest message, Responder responder, ImapSession session) {
        // TODO: See 6.2.2. AUTHENTICATE Command (RFC 3501)
    }

}
