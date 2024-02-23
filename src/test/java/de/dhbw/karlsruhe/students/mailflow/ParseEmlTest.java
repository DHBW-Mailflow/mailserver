package de.dhbw.karlsruhe.students.mailflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import de.dhbw.karlsruhe.students.mailflow.core.external.email.EmlParser;

import jakarta.mail.MessagingException;

public class ParseEmlTest {
    @Test
    public void parsesEmlFileToEmailModel() throws MessagingException, IOException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("""
                From: someone@example.com
                To: someone_else@example.com
                Subject: An RFC 822 formatted message

                This is the plain text body of the message. Note the blank line
                between the header information and the body of the message.""".getBytes(StandardCharsets.UTF_8));

        // Act
        EmlParser parser = new EmlParser();
        parser.setInputStream(inputStream);

        Email email = parser.parseToEmail();

        // Assert
        assertNotNull(email);

        assertEquals(email.getEmailMetadata().sender(), new Address("someone", "example.com"));

        assertEquals(email.getEmailMetadata().recipients().to(), List.of(new Address("someone_else", "example.com")));
        assertEquals(email.getEmailMetadata().recipients().cc(), Collections.emptyList());
        assertEquals(email.getEmailMetadata().recipients().bcc(), Collections.emptyList());

        assertEquals(email.getEmailMetadata().subject(), new Subject("An RFC 822 formatted message"));
        assertEquals(email.getContent(), "This is the plain text body of the message. Note the blank line\n" + //
                "between the header information and the body of the message.");
    }
}
