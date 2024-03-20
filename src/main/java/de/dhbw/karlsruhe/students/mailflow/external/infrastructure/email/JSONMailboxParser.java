package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import static de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.GSONConfiguration.getConfiguredGson;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public class JSONMailboxParser implements MailboxParser {

  @Override
  public Mailbox parseMailbox(String jsonString) throws MailboxParsingException {
    try {
      return getConfiguredGson().fromJson(jsonString, Mailbox.class);
    } catch (Exception e) {
      throw new MailboxParsingException("Could not parse the given content to a Mailbox object", e);
    }
  }
}
