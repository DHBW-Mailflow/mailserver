package de.dhbw.karlsruhe.students.mailflow.core.usecase;

import java.io.InputStream;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;

public interface EmailParser {
    public void setInputStream(InputStream inputStream);

    public Email parseToEmail() throws EmailParsingException;
}
