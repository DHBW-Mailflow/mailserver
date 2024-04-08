package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import org.fest.util.VisibleForTesting;

/**
 * @author Jonas-Karl
 */
public class FileMailboxRepository implements MailboxRepository {
  private final File allMailboxesDirectory;
  private final MailboxConverter mailboxSerializer;

  @VisibleForTesting
  public FileMailboxRepository(MailboxConverter mailboxSerializer, File allMailboxesDirectory) {
    Logger.getLogger(FileMailboxRepository.class.getName())
        .warning("VisibleForTesting method called");
    this.mailboxSerializer = mailboxSerializer;
    this.allMailboxesDirectory = allMailboxesDirectory;
  }

  public FileMailboxRepository(MailboxConverter mailboxSerializer) {
    this.mailboxSerializer = mailboxSerializer;
    this.allMailboxesDirectory = new File("mailboxes");
  }

  @Override
  public Mailbox findByAddressAndType(Address address, MailboxType type)
      throws MailboxLoadingException, MailboxSavingException {
    File mailboxFile = getOrCreateFile(address, type);
    return mailboxSerializer.deserializeMailboxFile(mailboxFile);
  }

  @Override
  public void save(Mailbox mailbox) throws MailboxSavingException {
    String mailboxContent = mailboxSerializer.serializeMailbox(mailbox);
    File createdMailboxFile = getOrCreateFile(mailbox.getOwner(), mailbox.getType());
    writeContentToFile(createdMailboxFile, mailboxContent);
  }

  /**
   * overrides existing content of provided file
   *
   * @param createdMailboxFile the file to write the content to
   * @param mailboxContent the content to write
   * @throws MailboxSavingException when a error occurred during the writing process
   */
  private void writeContentToFile(File createdMailboxFile, String mailboxContent)
      throws MailboxSavingException {
    try (FileWriter writer = new FileWriter(createdMailboxFile)) {
      writer.write(mailboxContent); // override existing content
    } catch (IOException e) {
      throw new MailboxSavingException(
          "Could not save mailbox content to file: " + mailboxContent, e);
    }
  }

  /**
   * @param address the address of the mailbox owner
   * @param type the type of the mailbox like INBOX, SENT, etc.
   * @return the file where the mailbox is stored
   * @throws MailboxSavingException when a error occurred during the creation process
   */
  private File getOrCreateFile(Address address, MailboxType type) throws MailboxSavingException {
    File directoryOfUser = new File(allMailboxesDirectory, address.toString());
    File mailboxFile = new File(directoryOfUser, type.getStoringName() + ".json");
    if (mailboxFile.exists()) {
      return mailboxFile;
    }
    return createMailboxFile(mailboxFile);
  }

  /**
   * Creates needed directories and the file itself
   *
   * @param mailboxFile The file to create
   * @return the created file
   * @throws MailboxSavingException if the file could not be created
   */
  private File createMailboxFile(File mailboxFile) throws MailboxSavingException {
    try {
      if (!mailboxFile.getParentFile().mkdirs() || !mailboxFile.createNewFile()) {
        throw new MailboxSavingException(mailboxFile.getPath());
      }
      return mailboxFile;
    } catch (IOException e) {
      throw new MailboxSavingException(mailboxFile.getPath(), e);
    }
  }
}
