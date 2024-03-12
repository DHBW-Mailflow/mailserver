package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParser;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import jakarta.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import jakarta.mail.MessagingException;
import javax.mail.Session;
import jakarta.mail.Store;
import javax.mail.URLName;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.fortuna.mstor.model.MStorStore;

public class MboxParser implements MailboxParser {

  private List<Email> emails = new ArrayList<>();


  @Override
  public Mailbox parseMailbox(File file, Address address) throws MailboxParsingException {

    Message [] messages = null;

    try {
      var properties = new Properties();
      properties.setProperty("mail.store.protocol", "mstor");
      Session session = Session.getDefaultInstance(properties);
      URLName mboxPath = new URLName("mstor:" + file.getPath());
      MStorStore store = new MStorStore(session, mboxPath);
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

}
