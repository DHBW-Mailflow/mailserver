package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxCreationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxDoesNotExistException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxWritingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.fest.util.VisibleForTesting;

public class LocalMailboxRepository implements MailboxRepository {

  private final File directoryOfAllUsers;

  public LocalMailboxRepository() {
    this.directoryOfAllUsers = new File("storage/filestorage/mailboxes");
  }

  @VisibleForTesting
  public LocalMailboxRepository(File directoryOfAllUsers) {
    this.directoryOfAllUsers = directoryOfAllUsers;
  }
  @Override
  public File provideStoredMailboxFileFor(Address userAddress, MailboxType type) throws MailboxDoesNotExistException {
    if (!directoryOfAllUsers.exists()) {
      throw new MailboxDoesNotExistException("Directory of all users does not exist");
    }
    File directoryOfUser = new File(directoryOfAllUsers, userAddress.toString());
    if (!directoryOfUser.exists()) {
      throw new MailboxDoesNotExistException(
          String.format("Directory of user %s does not exist", userAddress));
    }
    File mailbox = new File(directoryOfUser, type.getFileSuffix() + ".json");
    if (!directoryOfUser.exists()) {
      throw new MailboxDoesNotExistException(
          String.format(
              "Mailbox of type %s does not exist for user %s", type.getFileSuffix(), userAddress));
    }
    return mailbox;
  }

  @Override
  public File saveMailbox(Mailbox mailbox)
      throws MailboxCreationException, MailboxWritingException {
    JSONMailboxCreator creator = new JSONMailboxCreator();
    String mailboxContent = creator.generateMailboxContent(mailbox);
    File createdMailboxFile = getOrCreateMailboxFile(mailbox);
    return writeContentToFile(createdMailboxFile, mailboxContent);
  }

  private File writeContentToFile(File createdMailboxFile, String mailboxContent)
      throws MailboxWritingException {
    try (FileWriter writer = new FileWriter(createdMailboxFile)) {
      writer.write(mailboxContent); // override existing content
      return createdMailboxFile;
    } catch (IOException e) {
      throw new MailboxWritingException("Could not save mailbox to file: " + mailboxContent, e);
    }
  }

  private File getOrCreateMailboxFile(Mailbox mailbox) throws MailboxCreationException {
    File directoryOfUser = new File(directoryOfAllUsers, mailbox.getOwner().toString());
    File mailboxFile = new File(directoryOfUser, mailbox.getType().getFileSuffix() + ".json");
    if (!directoryOfUser.exists()) {
      if (!directoryOfUser.mkdirs()) {
        throw new MailboxCreationException(
            "Could not create directory" + directoryOfUser.getPath());
      }
    }
    try {
      mailboxFile.createNewFile(); // only if it does not exist
      return mailboxFile;
    } catch (IOException e) {
      throw new MailboxCreationException("Could not create new File" + mailboxFile.getPath());
    }
  }
}
