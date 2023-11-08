package de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.Entity;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.valueObjects.AddressId;

/**
 * Representation of an e-mail address, consisting of a localPart and a domain,
 * separated by an `@` sign.
 */
public class Address extends Entity<AddressId> {
    private String localPart;
    private String domain;

    private Address(AddressId id, String localPart, String domain) {
        super(id);
        this.localPart = localPart;
        this.domain = domain;
    }

    public static Address create(String localPart, String domain) {
        return new Address(AddressId.CreateUnique(), localPart, domain);
    }

    public String getLocalPart() {
        return localPart;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return getLocalPart() + "@" + getDomain();
    }
}