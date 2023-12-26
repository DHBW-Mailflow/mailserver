package de.dhbw.karlsruhe.students.mailflow.core.external.email;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Header;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Message.RecipientType;

public class EmailMetadataFactory {
    private Message message;

    public EmailMetadataFactory(Message message) {
        this.message = message;
    }

    public static EmailMetadataFactory withMessage(Message message) {
        EmailMetadataFactory factory = new EmailMetadataFactory(message);
        return factory;
    }

    private List<Address> getRecipients(RecipientType recipientType) throws MessagingException {
        List<Address> addresses = new ArrayList<>();

        jakarta.mail.Address[] recipients = message.getRecipients(recipientType);

        if (recipients == null || recipients.length == 0) {
            return Collections.emptyList();
        }

        for (jakarta.mail.Address address : recipients) {
            addresses.add(this.getAddress(address));
        }

        return addresses;
    }

    private Address getAddress(jakarta.mail.Address address) {
        String addressString = address.toString();

        int i = addressString.lastIndexOf("@");

        return new Address(addressString.substring(0, i), addressString.substring(i + 1));
    }

    private List<Header> getHeaders() throws MessagingException {
        List<Header> headers = new ArrayList<>();

        this.message.getAllHeaders().asIterator().forEachRemaining(x -> {
            headers.add(new Header(x.getName(), x.getValue()));
        });

        return headers;
    }

    public EmailMetadata build() throws MessagingException {
        return new EmailMetadata(
                new Subject(message.getSubject()),
                this.getAddress(message.getFrom()[0]),
                this.getHeaders(),
                new Recipients(this.getRecipients(RecipientType.TO), this.getRecipients(RecipientType.CC),
                        this.getRecipients(RecipientType.BCC)),
                new SentDate(message.getSentDate()));
    }
}
