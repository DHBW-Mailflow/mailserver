package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public class JSONMailboxParser implements MailboxParser {

  @Override
  public Mailbox parseMailbox(String jsonString) throws MailboxParsingException {
    Gson gson = new GsonBuilder()
        .setDateFormat(GSONConfiguration.getTimeFormat())
        .create();
    try {
      return gson.fromJson(jsonString, Mailbox.class);
    } catch (Exception e) {
      throw new MailboxParsingException("Could not parse the given content to a Mailbox object", e);
    }
  }
}
