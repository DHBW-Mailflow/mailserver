package de.dhbw.karlsruhe.students.mailflow.exceptions;

public class AddressParsingException extends Exception {

    protected String malformedAddress;

    public AddressParsingException(String malformedAddress) {
        super("Couldn't parse address " + malformedAddress);

        this.malformedAddress = malformedAddress;
    }

    public String getMalformedAddress() {
        return malformedAddress;
    }
}
