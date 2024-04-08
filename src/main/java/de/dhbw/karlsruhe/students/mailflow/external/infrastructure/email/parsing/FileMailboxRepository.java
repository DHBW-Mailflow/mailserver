package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxDoesNotExistException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import org.fest.util.VisibleForTesting;

public class FileMailboxRepository implements MailboxRepository {
  private final File allMailboxesDirectory;
  private final MailboxConverter mailboxSerializer;

  @VisibleForTesting
  public FileMailboxRepository(MailboxConverter mailboxSerializer, File allMailboxesDirectory) {
    this.mailboxSerializer = mailboxSerializer;
    Logger.getLogger(FileMailboxRepository.class.getName())
        .warning("VisibleForTesting method called");
    this.allMailboxesDirectory = allMailboxesDirectory;
  }

  public FileMailboxRepository(MailboxConverter mailboxSerializer) {
    this.mailboxSerializer = mailboxSerializer;
    this.allMailboxesDirectory = new File("mailboxes");
  }

  @Override
  public Mailbox findByAddressAndType(Address address, MailboxType type)
      throws MailboxDoesNotExistException, MailboxLoadingException {
    File mailboxFile = loadMailboxFile(address, type);
    if (!mailboxFile.exists()) {
      throw new MailboxDoesNotExistException("Mailbox file does not exist");
    }
    return mailboxSerializer.deserializeMailboxFile(mailboxFile);
  }

  @Override
  public void save(Mailbox mailbox) throws MailboxSavingException {
    String mailboxContent = mailboxSerializer.serializeMailbox(mailbox);
    File createdMailboxFile = getOrCreateMailboxFile(mailbox);
    writeContentToFile(createdMailboxFile, mailboxContent);
  }

  private void writeContentToFile(File createdMailboxFile, String mailboxContent)
      throws MailboxSavingException {
    try (FileWriter writer = new FileWriter(createdMailboxFile)) {
      writer.write(mailboxContent); // override existing content
    } catch (IOException e) {
      throw new MailboxSavingException("Could not save mailbox to file: " + mailboxContent, e);
    }
  }

  private File getOrCreateMailboxFile(Mailbox mailbox) throws MailboxSavingException {
    File directoryOfUser = new File(allMailboxesDirectory, mailbox.getOwner().toString());
    File mailboxFile = new File(directoryOfUser, mailbox.getType().getStoringName() + ".json");
    if (!directoryOfUser.exists() && !directoryOfUser.mkdirs()) {
      throw new MailboxSavingException("Could not create directory" + directoryOfUser.getPath());
    }
    if (mailboxFile.exists()) {
      return mailboxFile;
    }
    try {
      if (!mailboxFile.createNewFile()) {
        throw new MailboxSavingException("Could not create mailbox file" + mailboxFile.getPath());
      }
      return mailboxFile;
    } catch (IOException e) {
      throw new MailboxSavingException("Could not create new mailbox File" + mailboxFile.getPath());
    }
  }

  private File loadMailboxFile(Address address, MailboxType type)
      throws MailboxDoesNotExistException {

    if (!allMailboxesDirectory.exists()) {
      throw new MailboxDoesNotExistException("Directory of all users does not exist");
    }

    File directoryOfUser = new File(allMailboxesDirectory, address.toString());

    if (!directoryOfUser.exists()) {
      throw new MailboxDoesNotExistException(
          String.format("Directory of user %s does not exist", address));
    }

    File mailbox = new File(directoryOfUser, type.getStoringName() + ".json");

    if (!directoryOfUser.exists()) {
      throw new MailboxDoesNotExistException(
          String.format(
              "Mailbox of type %s does not exist for user %s", type.getStoringName(), address));
    }
    return mailbox;
  }
}
