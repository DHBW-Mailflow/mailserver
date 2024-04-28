package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.HeaderKey;
import java.util.List;
import java.util.UUID;

/**
 * Representation of constant e-mail, which will exist with the creation of an e-mail
 *
 * @author Jonas-Karl
 */
public record EmailMetadata(
    Subject subject,
    Address sender,
    List<Header> headers,
    Recipients recipients,
    SentDate sentDate) {

  public static EmailMetadata withNewId(EmailMetadata metadata, UUID id) {
    final List<Header> cleanedHeaders =
        metadata.headers().stream()
            .filter(header -> !header.key().equals(HeaderKey.MESSAGE_ID.getKeyName()))
            .toList();
    Header idHeader = new Header(HeaderKey.MESSAGE_ID.getKeyName(), id.toString());
    cleanedHeaders.add(idHeader);

    return new EmailMetadata(
        metadata.subject(),
        metadata.sender(),
        cleanedHeaders,
        metadata.recipients(),
        metadata.sentDate());
  }

  public EmailMetadata withoutBCCRecipients() {
    return new EmailMetadata(subject, sender, headers, recipients.withoutBCCRecipients(), sentDate);
  }
}
