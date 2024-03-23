package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.creator.FileCreationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.creator.FileWritingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class LocalMailboxRepository implements MailboxRepository {

  private static final String LOCAL_FILE_STORAGE_PATH = "storage/filestorage/mailboxes";

  public static File getPreviouslyStoredMailboxFile(Address userAddress, MailboxType type) {
    File directoryOfAllUsers = new File(LOCAL_FILE_STORAGE_PATH);
    File directoryOfUser = new File(directoryOfAllUsers, userAddress.toString());
    return new File(directoryOfUser, type.getFileSuffix() + ".json");
  }

  @Override
  public Optional<File> provideStoredMailboxFileFor(Address userAddress, MailboxType type) {
    File mboxFile = getPreviouslyStoredMailboxFile(userAddress, type);

    if (!mboxFile.exists()) {
      return Optional.empty();
    }

    return Optional.of(mboxFile);
  }
  @Override
  public File saveMailbox(Mailbox mailbox) throws FileCreationException, FileWritingException {
    JSONMailboxCreator creator = new JSONMailboxCreator();
    String mailboxContent = creator.generateMailboxContent(mailbox);
    File createdMailboxFile = getOrCreateMailboxFile(mailbox);
    return writeContentToFile(createdMailboxFile, mailboxContent);
}

  private File writeContentToFile(File createdMailboxFile, String mailboxContent)
      throws FileWritingException {
    try (FileWriter writer = new FileWriter(createdMailboxFile)) {
      writer.write(mailboxContent); // override existing content
      return createdMailboxFile;
    } catch (IOException e) {
      throw new FileWritingException("Could not save mailbox to file: " + mailboxContent, e);
    }
  }

  private File getOrCreateMailboxFile(Mailbox mailbox) throws FileCreationException {
    File directoryOfAllUsers = new File(LOCAL_FILE_STORAGE_PATH);
    File directoryOfUser = new File(directoryOfAllUsers, mailbox.getOwner().toString());
    File mailboxFile= new File(directoryOfUser, mailbox.getType().getFileSuffix() + ".json");
    if (!directoryOfUser.exists()) {
      if (!directoryOfUser.mkdirs()) {
        throw new FileCreationException("Could not create directory" + directoryOfUser.getPath());
      }
    }
    try {
      mailboxFile.createNewFile(); //only if it does not exist
      return mailboxFile;
    } catch (IOException e) {
      throw new FileCreationException("Could not create new File" + mailboxFile.getPath());
    }
  }
}
