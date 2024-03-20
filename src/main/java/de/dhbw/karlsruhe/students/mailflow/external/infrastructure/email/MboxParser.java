package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import com.google.gson.Gson;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public class MboxParser implements MailboxParser {

  @Override
  public Mailbox parseMailbox(String content) throws MailboxParsingException {
    Gson gson = new Gson();
    try {
      return gson.fromJson(content, Mailbox.class);
    } catch (Exception e) {
      throw new MailboxParsingException("Could not parse the given content to a Mailbox object", e);
    }
  }
}
