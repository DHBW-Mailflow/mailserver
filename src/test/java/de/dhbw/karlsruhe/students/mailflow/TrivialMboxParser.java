package de.dhbw.karlsruhe.students.mailflow;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.james.mime4j.Charsets;
import org.apache.james.mime4j.mboxiterator.CharBufferWrapper;
import org.apache.james.mime4j.mboxiterator.FromLinePatterns;
import org.apache.james.mime4j.mboxiterator.MboxIterator;
import org.junit.jupiter.api.Test;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.EmlParser;

class TrivialMboxParser {

    @Test
    void iGotNoProblems() throws FileNotFoundException, IOException {
        String path = "src/test/java/de/dhbw/karlsruhe/students/mailflow/Daily_Problem.mbox";
        try (MboxIterator iterator = MboxIterator.fromFile(new File(path)).fromLine("^From: \\S+.*\\d{4}$")
                .build()) {
            for (CharBufferWrapper message : iterator) {
                EmlParser parser = new EmlParser();
                Email email = parser.parseToEmail(message.asInputStream(Charsets.UTF_8));

                System.out.println(email);
            }
        }

    }
}
