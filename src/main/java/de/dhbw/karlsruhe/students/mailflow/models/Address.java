package de.dhbw.karlsruhe.students.mailflow.models;

import de.dhbw.karlsruhe.students.mailflow.exceptions.AddressParsingException;

/**
 * Representation of an e-mail address, consisting of a localPart and a domain,
 * separated by an `@` sign.
 */
public class Address {
    protected String localPart;
    protected String domain;

    public Address(String localPart, String domain) {
        this.localPart = localPart;
        this.domain = domain;
    }

    public static Address parse(String address) throws AddressParsingException {
        // get position of last @
        int indexOfLastAtSign = address.lastIndexOf("@");

        if (indexOfLastAtSign == -1) {
            throw new AddressParsingException(address);
        }

        return new Address(
                address.substring(0, indexOfLastAtSign),
                address.substring(indexOfLastAtSign + 1));
    }

    public String getLocalPart() {
        return localPart;
    }

    public String getDomain() {
        return domain;
    }

    public String toString() {
        return getLocalPart() + "@" + getDomain();
    }
}