package de.dhbw.karlsruhe.students.mailflow.core.external.email;

import java.io.IOException;
import java.io.InputStream;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

/**
 * Implements "RFC822: Standard for ARPA Internet Text Messages"
 * by parsing .eml-files
 */
public class EmlParser implements Parser {
    private InputStream inputStream;

    public Email parseToEmail() throws MessagingException, IOException {
        Session session = Session.getInstance(System.getProperties(), null);
        Message message = new MimeMessage(session, this.inputStream);

        return Email.create(message.getContent().toString(),
                EmailMetadataFactory.withMessage(message).build(), null);
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
