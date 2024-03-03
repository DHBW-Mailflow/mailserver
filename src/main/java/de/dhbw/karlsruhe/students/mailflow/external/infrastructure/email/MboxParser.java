package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.URLName;
import java.nio.file.Path;

public class MboxParser implements MailboxParser {

  @Override
  public Mailbox parseMailbox(Path path) throws MailboxParsingException {

    try {
      Session session = Session.getInstance(System.getProperties(), null);
      URLName mboxPath = new URLName("mbox:" + path.toString());
      Folder folder = session.getFolder(mboxPath);
      folder.open(Folder.READ_ONLY);
      Message [] messages = folder.getMessages();


    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
