package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.creator.FileCreationException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.creator.FileWritingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxRepository;
import java.io.File;
import java.util.List;
import java.util.Optional;
import org.fest.assertions.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class LocalMailboxRepositoryTest {

  @Test
  void saveMailboxtest() throws FileCreationException, FileWritingException {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");
    MailboxRepository repository = new LocalMailboxRepository();

    // Act
    File storedFile = repository.saveMailbox(Mailbox.create(mailboxOwner, List.of(), MailboxType.READ));

    // Assert
    Assertions.assertThat(storedFile.exists()).isTrue();


    //TODO get idea to avoid cleanup
    //clean up
    storedFile.getParentFile().delete();
    storedFile.delete();

  }

  @ParameterizedTest(name = "Retrieve from correct path for type {0}")
  @EnumSource(MailboxType.class)
  void retrieveFromCorrectPath(MailboxType mailboxType) {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");

    // Needed to test the path of a non-existing file in test environment
    MailboxRepository ownMailboxRepository = new MailboxRepository() {
      @Override
      public Optional<File> provideStoredMailboxFileFor(Address userAddress, MailboxType type) {
        return Optional.of(LocalMailboxRepository.getPreviouslyStoredMailboxFile(userAddress, type));
      }

      @Override
      public File saveMailbox(Mailbox mailbox) {
       // not tested here
        return null;
      }
    };

    // Act
    Optional<File> mailboxFile =
        ownMailboxRepository.provideStoredMailboxFileFor(mailboxOwner, mailboxType);
    String path = mailboxFile.get().getPath();

    // Assert
    Assertions.assertThat(path)
        .contains("filestorage")
        .contains(mailboxOwner.toString())
        .endsWith(mailboxType.getFileSuffix() + ".json");
  }

  @ParameterizedTest(name = "Return empty optional with non-existing file for type {0}")
  @EnumSource(MailboxType.class)
  void returnEmptyOptionalWithNonExistingFile(MailboxType mailboxType) {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");

    // Needed to test the path of a non-existing file
    MailboxRepository repository = new LocalMailboxRepository();

    // Act
    Optional<File> mailboxFile = repository.provideStoredMailboxFileFor(mailboxOwner, mailboxType);

    // Assert
    Assertions.assertThat(mailboxFile.isEmpty()).isTrue();
  }
}
