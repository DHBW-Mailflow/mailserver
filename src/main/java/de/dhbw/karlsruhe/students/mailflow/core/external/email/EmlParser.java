package de.dhbw.karlsruhe.students.mailflow.core.external.email;

import java.io.IOException;
import java.io.InputStream;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.usecase.EmailParser;
import de.dhbw.karlsruhe.students.mailflow.core.usecase.EmailParsingException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

/**
 * Implements "RFC822: Standard for ARPA Internet Text Messages"
 * by parsing .eml-files
 */
public class EmlParser implements EmailParser {
    private InputStream inputStream;

    public Email parseToEmail() throws EmailParsingException {
        Session session = Session.getInstance(System.getProperties(), null);
        Message message;
        try {
            message = new MimeMessage(session, this.inputStream);
        } catch (MessagingException e) {
            throw new EmailParsingException("couldn't parse .eml file stream", e);
        }

        try {
            return Email.create(message.getContent().toString(),
                    EmailMetadataFactory.withMessage(message).build(), null);
        } catch (IOException | MessagingException e) {
            throw new EmailParsingException("couldn't build final email", e);
        }
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
