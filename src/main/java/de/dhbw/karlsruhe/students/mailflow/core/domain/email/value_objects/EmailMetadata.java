package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.List;

/**
 * Representation of constant e-mail, which will exist with the creation of an e-mail
 *
 * @author Jonas-Karl
 */
public record EmailMetadata(Subject subject, Address sender, List<Header> headers,
        Recipients recipients, SentDate sentDate) {

    public EmailMetadata withoutBCCRecipients() {
        return new EmailMetadata(subject, sender, headers, recipients.withoutBCCRecipients(),
                sentDate);
    }
}
