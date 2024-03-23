package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxCreationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxDoesNotExistException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxWritingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.fest.assertions.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class LocalMailboxRepositoryTest {

  @TempDir File baseDirectory;

  @Test
  void saveMailboxtest() throws MailboxCreationException, MailboxWritingException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    MailboxRepository repository = new LocalMailboxRepository(baseDirectory);

    // Act
    File storedFile = repository.saveMailbox(Mailbox.create(mailboxOwner, List.of(), MailboxType.READ));

    // Assert
    Assertions.assertThat(storedFile.exists()).isTrue();

  }

  @ParameterizedTest(name = "Retrieve from correct path for type {0}")
  @EnumSource(MailboxType.class)
  void retrieveFromCorrectPath(MailboxType mailboxType) throws MailboxDoesNotExistException, IOException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    File userDirectory = new File(baseDirectory, mailboxOwner.toString());
    userDirectory.mkdirs();
    File existingMailboxFile = new File(userDirectory, mailboxType.getFileSuffix() + ".json");
    existingMailboxFile.createNewFile();

    // Needed to test the path of a non-existing file in test environment
    MailboxRepository ownMailboxRepository = new LocalMailboxRepository(baseDirectory);

    // Act
    var mailboxFile = ownMailboxRepository.provideStoredMailboxFileFor(mailboxOwner, mailboxType);
    String path = mailboxFile.getPath();

    // Assert
    Assertions.assertThat(path)
        .contains(mailboxOwner.toString())
        .endsWith(mailboxType.getFileSuffix() + ".json");
  }

  @ParameterizedTest(name = "Return empty optional with non-existing file for type {0}")
  @EnumSource(MailboxType.class)
  void throwWhenRetrievingNonExistingFile(MailboxType mailboxType) {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");

    // Needed to test the path of a non-existing file
    MailboxRepository repository = new LocalMailboxRepository(baseDirectory);

    // Act
    assertThrows(MailboxDoesNotExistException.class, () -> {
      // Assert
      repository.provideStoredMailboxFileFor(mailboxOwner, mailboxType);
    });

  }
}
