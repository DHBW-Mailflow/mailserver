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

    /**
     * @param emailString email to parse
     * @return the address object of the given email
     * @throws IllegalArgumentException when no @-sign was provided
     */
    public static Address from(String emailString) {
        int index = emailString.lastIndexOf("@");
        if (index == -1) {
            throw new IllegalArgumentException("No \"@\" was provided in %s".formatted(emailString));
        }
    return new Address(
        emailString.substring(0, index).toLowerCase(),
        emailString.substring(index + 1).toLowerCase());
    }
}
