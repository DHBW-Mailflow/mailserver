package de.dhbw.karlsruhe.students.mailflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.dhbw.karlsruhe.students.mailflow.exceptions.AddressParsingException;
import de.dhbw.karlsruhe.students.mailflow.models.Address;

import org.junit.jupiter.api.Test;

public class AddressTest {
    @Test
    public void shouldOutputReadableName() {
        Address address = new Address("manuel", "mueller.de");

        assertEquals("manuel@mueller.de", address.toString());
    }

    @Test
    public void shouldParseValidAddress() throws AddressParsingException {
        Address address = Address.parse("manuel@mueller.de");

        assertEquals("manuel", address.getLocalPart());
        assertEquals("mueller.de", address.getDomain());
    }

    @Test()
    public void shouldThrowInvalidAdress() {
        assertThrows(AddressParsingException.class, () -> {
            Address.parse("mueller.de");
        });
    }
}
