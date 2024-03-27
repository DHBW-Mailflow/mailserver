package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxFileProvider;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.LocalMailboxFileProvider;
import java.io.File;
import java.util.Optional;
import org.fest.assertions.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * @author Jonas-Karl
 */
class LocalMailboxFileProviderTest {

  @ParameterizedTest(name = "Retrieve from correct path for type {0}")
  @EnumSource(MailboxType.class)
  void retrieveFromCorrectPath(MailboxType mailboxType) {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");

    // Needed to test the path of a non-existing file in test environment
    MailboxFileProvider ownMailboxFileProvider =
        (userAddress, type) -> Optional.of(LocalMailboxFileProvider.getFile(userAddress, type));

    Optional<File> mailboxFile =
        ownMailboxFileProvider.provideStoredMailboxFileFor(mailboxOwner, mailboxType);

    // Act
    String path = mailboxFile.get().getPath();

    // Assert
    Assertions.assertThat(path).contains("filestorage").contains(mailboxOwner.toString())
        .endsWith(mailboxType.getFileSuffix() + ".json");
  }

  @ParameterizedTest(name = "Return empty optional with non-existing file for type {0}")
  @EnumSource(MailboxType.class)
  void returnEmptyOptionalWithNonExistingFile(MailboxType mailboxType) {
    // Arrange
    Address mailboxOwner = new Address("someOwner", "someDomain.de");

    // Needed to test the path of a non-existing file
    MailboxFileProvider fileProvider = new LocalMailboxFileProvider();

    // Act
    Optional<File> mailboxFile =
        fileProvider.provideStoredMailboxFileFor(mailboxOwner, mailboxType);

    // Assert
    Assertions.assertThat(mailboxFile.isEmpty()).isTrue();
  }
}
