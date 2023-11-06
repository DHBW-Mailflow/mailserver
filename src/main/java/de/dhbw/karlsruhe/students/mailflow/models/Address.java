package de.dhbw.karlsruhe.students.mailflow.models;

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