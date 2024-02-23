package de.dhbw.karlsruhe.students.mailflow.core.external.email;

import java.io.IOException;
import java.io.InputStream;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import jakarta.mail.MessagingException;

public interface Parser {
    public void setInputStream(InputStream inputStream);

    public Email parseToEmail() throws MessagingException, IOException;
}
