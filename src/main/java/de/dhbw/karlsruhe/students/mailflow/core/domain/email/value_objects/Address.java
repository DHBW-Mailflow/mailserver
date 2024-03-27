package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

/**
 * Representation of an e-mail address, consisting of a localPart and a domain, separated by an `@`
 * sign.
 *
 * @author jens1o
 */
public record Address(String localPart, String domain) {
    @Override
    public String toString() {
        return localPart + "@" + domain;
    }
}
