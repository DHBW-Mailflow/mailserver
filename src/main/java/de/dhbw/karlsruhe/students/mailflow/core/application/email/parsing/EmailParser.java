package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

import java.io.InputStream;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;

/**
 * @author jens1o
 */
public interface EmailParser {
    Email parseToEmail(InputStream inputStream) throws EmailParsingException;
}
