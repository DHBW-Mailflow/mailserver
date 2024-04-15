package de.dhbw.karlsruhe.students.mailflow.core.application.email;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * @author jens1o
 */
public class EmailSendService implements EmailSendUseCase {

    private MailboxRepository mailboxRepository;

    public EmailSendService(MailboxRepository mailboxRepository) {
        this.mailboxRepository = mailboxRepository;
    }

    @Override
    public void sendEmail(Email email) throws MailboxLoadingException, MailboxSavingException {
        // put the email into the SENT folder of the sender
        Mailbox mailbox =
                mailboxRepository.findByAddressAndType(email.getSender(), MailboxType.SENT);
        // sent emails do not need to be read again by the sender
        mailbox.deliverEmail(email, false);

        mailboxRepository.save(mailbox);

        // send it to To and CC recipients first...
        List<Address> addresses = collectToAndCcRecipients(email);
        // put the email into the INBOX folder of the respective recipient
        for (Address address : addresses) {
            Mailbox recipientMailbox =
                    mailboxRepository.findByAddressAndType(address, MailboxType.INBOX);
            recipientMailbox.deliverEmail(email, true);
            mailboxRepository.save(recipientMailbox);
        }

        // ...then strip the BCC recipients to uphold the guarantee that they don't see each other
        // mail addresses
        List<Address> bccAddresses = email.getRecipientBCC();
        email = email.withoutBCCRecipients();

        for (Address address : bccAddresses) {
            Mailbox recipientMailbox =
                    mailboxRepository.findByAddressAndType(address, MailboxType.INBOX);
            recipientMailbox.deliverEmail(email, true);
            mailboxRepository.save(recipientMailbox);
        }
    }

    private List<Address> collectToAndCcRecipients(Email email) {
        return Stream.of(email.getRecipientTo(), email.getRecipientCC()).flatMap(Collection::stream)
                .toList();
    }

}
