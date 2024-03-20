package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxRepository;
import java.io.File;
import java.util.Optional;
import org.fest.assertions.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class LocalMailboxRepositoryTest {

  @ParameterizedTest(name = "Retrieve from correct path for type {0}")
  @EnumSource(MailboxType.class)
  public void retrieveFromCorrectPath(MailboxType mailboxType) {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");

    // Needed to test the path of a non-existing file in test environment
    MailboxRepository ownMailboxRepository =
        (userAddress, type) ->
            Optional.of(LocalMailboxRepository.getFile(userAddress, type));

    Optional<File> mailboxFile =
        ownMailboxRepository.provideStoredMailboxFileFor(mailboxOwner, mailboxType);

    // Act
    String path = mailboxFile.get().getPath();

    // Assert
    Assertions.assertThat(path)
        .contains("filestorage")
        .contains(mailboxOwner.toString())
        .endsWith(mailboxType.getFileSuffix() + ".json");
  }

  @ParameterizedTest(name = "Return empty optional with non-existing file for type {0}")
  @EnumSource(MailboxType.class)
  public void returnEmptyOptionalWithNonExistingFile(MailboxType mailboxType) {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");

    // Needed to test the path of a non-existing file
    MailboxRepository repository = new LocalMailboxRepository();

    // Act
    Optional<File> mailboxFile = repository.provideStoredMailboxFileFor(mailboxOwner, mailboxType);

    // Assert
    Assertions.assertThat(mailboxFile.isEmpty());
  }
}
