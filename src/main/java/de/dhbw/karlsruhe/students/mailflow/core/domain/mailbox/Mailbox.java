package de.dhbw.karlsruhe.students.mailflow.core.domain.mailbox;

import java.util.HashSet;
import java.util.Set;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.mailbox.value_object.MailboxId;

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

    private Set<Email> emails;

    private String password;

    private Mailbox(MailboxId id, Address address, String name, String password) {
        super(id);
        this.emails = new HashSet<>();
        this.password = password;
        this.name = name;
        this.address = address;
    }

    public static Mailbox create(Address address, String name, String password) {
        return new Mailbox(MailboxId.createUnique(), address, name, password);
    }

    public Set<Email> getEmails() {
        return emails;
    }

    @Override
    public String toString() {
        if (this.name.length() == 0) {
            return this.address.toString();
        }

        return this.name + " <" + this.address.toString() + ">";
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
