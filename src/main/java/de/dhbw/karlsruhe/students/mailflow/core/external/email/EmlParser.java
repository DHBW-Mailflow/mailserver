package de.dhbw.karlsruhe.students.mailflow.core.external.email;

import java.io.IOException;
import java.io.InputStream;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailId;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

/**
 * Implements "RFC822: Standard for ARPA Internet Text Messages"
 * by parsing .eml-files
 */
public class EmlParser {
    private InputStream inputStream;

    public EmlParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Email parseToEmail() throws MessagingException, IOException {
        Session session = Session.getInstance(System.getProperties(), null);
        Message message = new MimeMessage(session, this.inputStream);

        Email mail = new Email(EmailId.createUnique(), message.getContent().toString(),
                EmailMetadataFactory.withMessage(message).build(), null);

        return mail;
    }
}
