package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;
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
      // @formatter:off
      throw new MailboxSavingException("Could not save mailbox content to file: " + mailboxContent, e);
      // @formatter:on
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
    return createMailboxFile(address, type, mailboxFile);
  }

  /**
   * Creates needed directories and the file itself
   *
   * @param address The address for which we need to create the inbox
   * @param type The mailbox type we need to create
   * @param mailboxFile The file to create
   * @return the created file
   * @throws MailboxSavingException if the file could not be created
   */
  private File createMailboxFile(Address address, MailboxType type, File mailboxFile)
      throws MailboxSavingException {
    try {
      if (!mailboxFile.getParentFile().mkdirs() && !mailboxFile.createNewFile()) {
        throw new MailboxSavingException(mailboxFile.getPath());
      }

      // initialize empty mailbox
      Mailbox mailbox = Mailbox.create(address, new HashMap<>(), type);
      writeContentToFile(mailboxFile, mailboxSerializer.serializeMailbox(mailbox));

      return mailboxFile;
    } catch (IOException e) {
      throw new MailboxSavingException(mailboxFile.getPath(), e);
    }
  }

  @Override
  public List<Mailbox> findAll() throws MailboxLoadingException, MailboxSavingException {
    List<Address> addresses =
        Stream.of(allMailboxesDirectory.listFiles(File::isDirectory)).map(dir -> {
          return Address.from(dir.getName());
        }).toList();

    List<Mailbox> mailboxes = new ArrayList<>();


    for (Address address : addresses) {
      for (MailboxType type : MailboxType.values()) {
        mailboxes.add(this.findByAddressAndType(address, type));
      }
    }

    return mailboxes;
  }
}
