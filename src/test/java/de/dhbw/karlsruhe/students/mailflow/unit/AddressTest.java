package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void shouldWork() {
        // Arrange
        String email = "some@mail.de";
        // Act
        Address result = Address.from(email);
        // Assert
        Assertions.assertEquals(new Address("some", "mail.de"), result);
    }

    @Test
    void shouldThrow() {
        // Arrange
        String email = "some-mail.de";
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                // Act
                () -> Address.from(email)
        );
    }
}