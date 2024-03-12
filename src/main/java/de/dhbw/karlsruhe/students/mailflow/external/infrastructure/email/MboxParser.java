package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.URLName;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MboxParser implements MailboxParser {

  private List<Email> emails = new ArrayList<>();

  private SessionProvider sessionProvider;
  private FolderProvider folderProvider;

  public MboxParser(SessionProvider sessionProvider, FolderProvider folderProvider) {
    this.sessionProvider = sessionProvider;
    this.folderProvider = folderProvider;
  }

  @Override
  public Mailbox parseMailbox(File file, Address address) throws MailboxParsingException {

    Message [] messages = null;

    try {
      Session session = Session.getInstance(System.getProperties(), null);
      URLName mboxPath = new URLName("mbox:" + file.getPath());
      Folder folder = session.getFolder(mboxPath);
      folder.open(Folder.READ_ONLY);
      messages = folder.getMessages();

    } catch (Exception e) {
      throw new MailboxParsingException("Could not connect to Usermailbox", e);
    }
    for (Message message : messages) {
      Email mail = CreateEmailHelper.createEmailWithMessage(message);
      emails.add(mail);
    }

    return Mailbox.create(address, emails);
  }

  public static interface SessionProvider {

    Session getInstance(Properties properties, Authenticator authenticator);

  }

  public static interface FolderProvider {

    Folder getFolder(URLName urlName) throws MessagingException;

  }
}
