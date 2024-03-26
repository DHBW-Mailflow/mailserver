package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import static de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.GSONConfiguration.getConfiguredGson;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.creator.MailboxCreator;

public class JSONMailboxCreator implements MailboxCreator {

  @Override
  public String generateMailboxContent(Mailbox mailbox) {
    return getConfiguredGson().toJson(mailbox);
  }

}
