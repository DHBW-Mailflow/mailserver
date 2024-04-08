package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxDoesNotExistException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.MailboxConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileMailboxRepositoryTest {
  private MailboxRepository fileMailboxRepository;
  private File allMailboxesDirectory;

  @BeforeEach
  void setUp(@TempDir File tempDir) {
    allMailboxesDirectory = tempDir;
  }

  @Test
  public void retrieveCorrectMailbox()
      throws MailboxLoadingException, MailboxDoesNotExistException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    MailboxType mailboxType = MailboxType.READ;
    Mailbox searchingMailbox = Mailbox.create(mailboxOwner, List.of(), mailboxType);
    File userDirectory = new File(allMailboxesDirectory, mailboxOwner.toString());
    File mailboxFile = new File(userDirectory, mailboxType + ".json");
    boolean successfullyCreatedDirectories = mailboxFile.mkdirs();

    MailboxConverter mockedMailboxConverter =
        new MailboxConverter() {
          @Override
          public String serializeMailbox(Mailbox mailbox) {
            // not tested
            return "someSerializedMailboxJson";
          }

          @Override
          public Mailbox deserializeMailboxFile(File mailboxFile) {
            return searchingMailbox;
          }
        };

    this.fileMailboxRepository =
        new FileMailboxRepository(mockedMailboxConverter, allMailboxesDirectory);
    // Act
    Mailbox foundMailbox = fileMailboxRepository.findByAddressAndType(mailboxOwner, mailboxType);
    // Assert
    Assertions.assertEquals(searchingMailbox, foundMailbox);
  }

  @Test
  public void searchingNonExistentMailboxShouldThrow() {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    MailboxType mailboxType = MailboxType.READ;
    Mailbox searchingMailbox = Mailbox.create(mailboxOwner, List.of(), mailboxType);

    MailboxConverter mockedMailboxConverter =
        new MailboxConverter() {
          @Override
          public String serializeMailbox(Mailbox mailbox) {
            // not tested
            return "someSerializedMailboxJson";
          }

          @Override
          public Mailbox deserializeMailboxFile(File mailboxFile) {
            return searchingMailbox;
          }
        };
    this.fileMailboxRepository =
        new FileMailboxRepository(mockedMailboxConverter, allMailboxesDirectory);

    // Assert
    Assertions.assertThrows(
        MailboxDoesNotExistException.class,
        // Act
        () -> fileMailboxRepository.findByAddressAndType(mailboxOwner, mailboxType));
  }

  @Test
  public void saveMailboxShouldCreateFile() throws MailboxSavingException, IOException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    MailboxType mailboxType = MailboxType.READ;
    Mailbox mailboxToSave = Mailbox.create(mailboxOwner, List.of(), mailboxType);

    String expectedSerializedMailboxJson = "someSerializedMailboxJson";
    MailboxConverter mockedMailboxConverter =
        new MailboxConverter() {
          @Override
          public String serializeMailbox(Mailbox mailbox) {
            return expectedSerializedMailboxJson;
          }

          @Override
          public Mailbox deserializeMailboxFile(File mailboxFile) {
            // not tested
            return mailboxToSave;
          }
        };
    this.fileMailboxRepository =
        new FileMailboxRepository(mockedMailboxConverter, allMailboxesDirectory);

    // Act
    fileMailboxRepository.save(mailboxToSave);

    // Assert
    File userDirectory = new File(allMailboxesDirectory, mailboxOwner.toString());
    File mailboxFile = new File(userDirectory, mailboxType + ".json");
    Assertions.assertTrue(mailboxFile.exists());

    String writtenContent = Files.readString(mailboxFile.toPath());
    Assertions.assertEquals(expectedSerializedMailboxJson, writtenContent);
  }
}
