package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.utils.FileHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import org.fest.util.VisibleForTesting;

/**
 * @author Jonas-Karl
 */
public class FileMailboxRepository implements MailboxRepository {
  private final File allMailboxesDirectory;
  private final MailboxConverter mailboxSerializer;
  private final FileHelper fileHelper;
  private String defaultFileContent;

  @VisibleForTesting
  public FileMailboxRepository(MailboxConverter mailboxSerializer, File allMailboxesDirectory) {
    Logger.getLogger(FileMailboxRepository.class.getName())
        .warning("VisibleForTesting method called");
    this.mailboxSerializer = mailboxSerializer;
    this.allMailboxesDirectory = allMailboxesDirectory;
    this.fileHelper = new FileHelper();
  }

  public FileMailboxRepository(MailboxConverter mailboxSerializer) {
    this.mailboxSerializer = mailboxSerializer;
    this.allMailboxesDirectory = new File("mailboxes");
    this.fileHelper = new FileHelper();
  }

  private void initFile(Mailbox mailbox) throws IOException {
    File mailboxFile = getFilePath(mailbox);

    if (fileHelper.existsFile(mailboxFile)) {
      return;
    }

    defaultFileContent = mailboxSerializer.serializeMailbox(mailbox);
    fileHelper.saveToFile(mailboxFile, defaultFileContent);
  }

  @Override
  public Mailbox findByAddressAndType(Address address, MailboxType type)
      throws MailboxLoadingException, MailboxSavingException {
    final File mailboxFile = getFilePath(address, type);
    final Mailbox defaultMailbox = Mailbox.create(address, new HashMap<>(), type);
    try {
      initFile(defaultMailbox);
    } catch (IOException e) {
      throw new MailboxSavingException("Could not initialize mailbox", e);
    }
    try {
      fileHelper.readFileContent(mailboxFile, defaultFileContent);
      return mailboxSerializer.deserializeMailboxFile(mailboxFile);
    } catch (IOException e) {
      throw new MailboxLoadingException("Could not load mailbox file", e);
    }
  }

  @Override
  public void save(Mailbox mailbox) throws MailboxSavingException {
    String mailboxContent = mailboxSerializer.serializeMailbox(mailbox);
    File filePath = getFilePath(mailbox);
    try {
      fileHelper.saveToFile(filePath, mailboxContent);
    } catch (IOException e) {
      throw new MailboxSavingException("Could not save mailbox file", e);
    }
  }

  @Override
  public List<Mailbox> findAllOtherInboxes(Address sender)
      throws MailboxLoadingException, MailboxSavingException {
    List<Address> addresses = filterOtherAddresses(sender);
    List<Mailbox> mailboxes = new ArrayList<>();

    for (Address address : addresses) {
      mailboxes.add(this.findByAddressAndType(address, MailboxType.INBOX));
    }

    return mailboxes;
  }

  private List<Address> filterOtherAddresses(Address sender) {
    File[] allMailboxDirs = allMailboxesDirectory.listFiles(File::isDirectory);
    if (allMailboxDirs == null || allMailboxDirs.length == 0) {
      return new ArrayList<>();
    }

    return Arrays.stream(allMailboxDirs)
        .filter(dir -> !dir.getName().equals(sender.toString()))
        .map(dir -> Address.from(dir.getName()))
        .toList();
  }

  private File getFilePath(Address mailboxOwner, MailboxType mailboxType) {
    File directoryOfUser = new File(allMailboxesDirectory, mailboxOwner.toString());
    return new File(directoryOfUser, mailboxType.getStoringName() + ".json");
  }

  private File getFilePath(Mailbox mailbox) {
    File directoryOfUser = new File(allMailboxesDirectory, mailbox.getOwner().toString());
    return new File(directoryOfUser, mailbox.getType().getStoringName() + ".json");
  }
}
