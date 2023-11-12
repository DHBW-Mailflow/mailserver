package de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.Entity;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.valueObjects.HeaderId;

/**
 * Representation of a custom e-mail header, consisting of a name and a value,
 */
public class Header extends Entity<HeaderId> {
    private String name;
    private String value;

    private Header(HeaderId id, String name, String value) {
        super(id);
        this.name = name;
        this.value = value;
    }

    public static Header create(String name, String value) {
        return new Header(HeaderId.createUnique(), name, value);
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}
