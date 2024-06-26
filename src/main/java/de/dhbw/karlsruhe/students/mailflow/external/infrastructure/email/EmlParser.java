package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.EmailParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.EmailParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.InputStream;

/**
 * Implements "RFC822: Standard for ARPA Internet Text Messages" by parsing .eml-files
 *
 * @author jens1o
 */
public class EmlParser implements EmailParser {
    @Override
    public Email parseToEmail(InputStream inputStream) throws EmailParsingException {
        Session session = Session.getInstance(System.getProperties(), null);
        Message message = getMessage(session, inputStream);
        return CreateEmailHelper.createEmailWithMessage(message);
    }

    private Message getMessage(Session session, InputStream inputStream)
            throws EmailParsingException {
        try {
            return new MimeMessage(session, inputStream);
        } catch (MessagingException e) {
            throw new EmailParsingException("couldn't parse .eml file stream", e);
        }

    }

}
