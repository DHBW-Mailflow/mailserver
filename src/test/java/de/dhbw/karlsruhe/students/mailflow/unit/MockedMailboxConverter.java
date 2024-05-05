package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.MailboxConverter;
import java.io.File;

public class MockedMailboxConverter implements MailboxConverter {

  private final Mailbox mailbox;

  private final String expectedSerializedMailboxJson;

  public MockedMailboxConverter(Mailbox mailbox, String expectedSerializedMailboxJson) {
    this.mailbox = mailbox;
    this.expectedSerializedMailboxJson = expectedSerializedMailboxJson;
  }

  @Override
  public Mailbox deserializeMailboxFile(File mailboxFile) {
    return mailbox;
  }

  @Override
  public String serializeMailbox(Mailbox mailbox) {
    return expectedSerializedMailboxJson;
  }
}
