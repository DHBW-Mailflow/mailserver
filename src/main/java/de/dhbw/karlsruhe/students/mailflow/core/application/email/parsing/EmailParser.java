package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import java.io.InputStream;

public interface EmailParser {
    Email parseToEmail(InputStream inputStream) throws EmailParsingException;
}
