package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.EmailParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.EmailParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.EmlParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author jens1o
 */
class ParseEmlTest {

        @Test
        void parsesEmlFileToEmailModel() {
                // Arrange
                InputStream inputStream = new ByteArrayInputStream("""
                                From: someone@example.com
                                To: someone_else@example.com
                                Subject: An RFC 822 formatted message
                                Date: Mon, 20 Mar 2024 11:02:50 +0000

                                This is the plain text body of the message. Note the blank line
                                between the header information and the body of the message."""
                                .getBytes(StandardCharsets.UTF_8));

                // Act
                EmailParser parser = new EmlParser();
                Email email = parser.parseToEmail(inputStream);

                // Assert
                assertNotNull(email);

                assertEquals(new Address("someone", "example.com"),
                                email.getSender());

                assertEquals(List.of(new Address("someone_else", "example.com")),
                                email.getRecipientTo());
                assertEquals(Collections.emptyList(), email.getRecipientCC());
                assertEquals(Collections.emptyList(), email.getRecipientBCC());

                assertEquals(new Subject("An RFC 822 formatted message"),
                                email.getSubject());

                assertEquals(SentDate.ofFormattedString("2024-03-20T11:02:50.00Z"),
                                email.getSendDate());
                assertEquals("This is the plain text body of the message. Note the blank line\n"
                                + "between the header information and the body of the message.",
                                email.getContent());
        }

        @Test
        void causesExceptionOnMalformedEmlFile() {
                // Arrange
                InputStream inputStream = new ByteArrayInputStream("""
                                From: someone@example.com
                                To: someone_e lse@example.com
                                Subject: An RFC 822 formatted message

                                This is the plain text body of the message. Note the blank line
                                between the header information and the body of the message."""
                                .getBytes(StandardCharsets.UTF_8));

                // Act
                EmailParser parser = new EmlParser();

                // Assert
                assertThrows(EmailParsingException.class, () -> parser.parseToEmail(inputStream));
        }
}
