package de.dhbw.karlsruhe.students.mailflow.core.domain.mailbox;

import java.util.HashSet;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.mailbox.valueObjects.MailboxId;

public class Mailbox extends AggregateRoot<MailboxId> {
    /**
     * The address of this mailbox, e.g. `manuel@mueller.de` in
     * {@literal Manuel Müller <manuel@mueller.de>}
     */
    private Address address;
    /**
     * The human readable name of this mailbox, e.g. `Manuel Müller` in
     * {@literal Manuel Müller <manuel@mueller.de>}
     */
    private String name;

    private String password;
    private HashSet<Email> emails;

    private Mailbox(MailboxId id, String password) {
        super(id);
        this.password = password;
        this.emails = new HashSet<>();
    }

    public static Mailbox create(String password) {
        return new Mailbox(MailboxId.CreateUnique(), password);
    }

    public HashSet<Email> getEmails() {
        return emails;
    }

    public String toString() {
        if (this.name.length() == 0) {
            return this.address.toString();
        }

        return this.name + " <" + this.address.toString() + ">";
    }
}
