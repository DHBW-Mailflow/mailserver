package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.util.Optional;

public class LocalMailboxRepository implements MailboxRepository {

  private static final String LOCAL_FILE_STORAGE_PATH =
      "src/main/java/de/dhbw/karlsruhe/students/mailflow/external/infrastructure/filestorage";

  public static File getFile(Address userAddress, MailboxType type) {
    File directoryOfAllUsers = new File(LOCAL_FILE_STORAGE_PATH);
    File directoryOfUser = new File(directoryOfAllUsers, userAddress.toString());
    return new File(directoryOfUser, type.getFileSuffix() + ".json");
  }

  @Override
  public Optional<File> provideStoredMailboxFileFor(Address userAddress, MailboxType type) {
    File mboxFile = getFile(userAddress, type);

    if (!mboxFile.exists()) {
      return Optional.empty();
    }

    return Optional.of(mboxFile);
  }
}
