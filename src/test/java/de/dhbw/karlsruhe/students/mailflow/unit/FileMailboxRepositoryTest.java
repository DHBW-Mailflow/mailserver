package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.MailboxConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * @author Jonas-Karl
 */
class FileMailboxRepositoryTest {
  private MailboxRepository fileMailboxRepository;
  private File allMailboxesDirectory;

  @BeforeEach
  void setUp(@TempDir File tempDir) {
    allMailboxesDirectory = tempDir;
  }

  @ParameterizedTest(name = "should retrieve correct mailboxType {0}")
  @EnumSource(MailboxType.class)
  void retrieveCorrectMailbox(MailboxType mailboxType)
      throws MailboxLoadingException, MailboxSavingException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    Mailbox searchingMailbox = Mailbox.create(mailboxOwner, Map.of(), mailboxType);
    File userDirectory = new File(allMailboxesDirectory, mailboxOwner.toString());
    File mailboxFile = new File(userDirectory, mailboxType.getStoringName() + ".json");
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

  @ParameterizedTest(name = "should create directories and files for mailboxType {0}")
  @EnumSource(MailboxType.class)
  void searchingNonExistentMailboxShouldCreate(MailboxType mailboxType)
      throws MailboxSavingException, MailboxLoadingException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    Mailbox searchingMailbox = Mailbox.create(mailboxOwner, Map.of(), mailboxType);
    File directoryOfUser = new File(allMailboxesDirectory, mailboxOwner.toString());
    File mailboxFile = new File(directoryOfUser, mailboxType.getStoringName() + ".json");

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
    Assertions.assertTrue(mailboxFile.exists());
  }

  @ParameterizedTest(name = "should create file for mailboxType {0}")
  @EnumSource(MailboxType.class)
  void saveMailboxShouldCreateFile(MailboxType mailboxType)
      throws MailboxSavingException, IOException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    Mailbox mailboxToSave = Mailbox.create(mailboxOwner, Map.of(), mailboxType);

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
    File mailboxFile = new File(userDirectory, mailboxType.getStoringName() + ".json");
    Assertions.assertTrue(mailboxFile.exists());

    String writtenContent = Files.readString(mailboxFile.toPath());
    Assertions.assertEquals(expectedSerializedMailboxJson, writtenContent);
  }
}
