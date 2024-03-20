package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.*;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;

import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class EmailMetadataFactory {

    private Message message;

    private EmailMetadataFactory(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message must not be null");
        }
        this.message = message;
    }

    public static EmailMetadataFactory withMessage(Message message) {
        return new EmailMetadataFactory(message);
    }

    public static EmailMetadataFactory withMessage(javax.mail.Message message) {
        Session session = Session.getDefaultInstance(new Properties(), null);
        Message jakartaMessage = new MimeMessage(session);
        try {
            jakartaMessage.setSubject(message.getSubject());
            jakartaMessage.setSentDate(message.getSentDate());
            jakartaMessage.setContent(message.getContent(), message.getContentType());
            jakartaMessage.setFrom(new InternetAddress(message.getFrom()[0].toString()));

            var toRecipients = message.getRecipients(javax.mail.Message.RecipientType.TO);

            if (toRecipients != null) {
                jakartaMessage.setRecipients(RecipientType.TO,
                        InternetAddress.parse(
                                getAddressString(toRecipients)));
            }

            var ccRecipients = message.getRecipients(javax.mail.Message.RecipientType.CC);

            if (ccRecipients != null) {
                jakartaMessage.setRecipients(RecipientType.CC,
                        InternetAddress.parse(
                                getAddressString(ccRecipients)));
            }

            var bccRecipients = message.getRecipients(javax.mail.Message.RecipientType.BCC);

            if (bccRecipients != null) {
                jakartaMessage.setRecipients(RecipientType.BCC,
                        InternetAddress.parse(
                                getAddressString(bccRecipients)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return EmailMetadataFactory.withMessage(jakartaMessage);
    }

    private List<Address> getRecipientAddresses(RecipientType recipientType)
            throws MessagingException {
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

        this.message
                .getAllHeaders()
                .asIterator()
                .forEachRemaining(x -> {
                    headers.add(new Header(x.getName(), x.getValue()));
                });

        return headers;
    }

    public EmailMetadata build() throws MessagingException {
        return new EmailMetadata(
                new Subject(message.getSubject()),
                this.getAddress(message.getFrom()[0]),
                this.getHeaders(),
                new Recipients(this.getRecipientAddresses(RecipientType.TO),
                        this.getRecipientAddresses(RecipientType.CC),
                        this.getRecipientAddresses(RecipientType.BCC)),
                new SentDate(message.getSentDate()));
    }

    private static String getAddressString(javax.mail.Address[] addresses) {
        if (addresses == null) {
            return "";
        }

        return List.of(addresses).stream()
                .map(x -> x.toString()).collect(Collectors.joining(","));
    }
}