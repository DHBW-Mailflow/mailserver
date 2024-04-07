package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxFileProvider;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import java.io.File;
import java.util.Optional;

/**
 * @author seiferla
 */
public class LocalMailboxFileProvider implements MailboxFileProvider {

  private static final String LOCAL_FILE_STORAGE_PATH = "storage/filestorage/mailboxes";

  public static File getFile(User user, MailboxType type) {
    File directoryOfAllUsers = new File(LOCAL_FILE_STORAGE_PATH);
    File directoryOfUser = new File(directoryOfAllUsers, user.toString());
    return new File(directoryOfUser, type.getFileSuffix() + ".json");
  }

  @Override
  public Optional<File> provideStoredMailboxFileFor(User user, MailboxType type) {
    File mboxFile = getFile(user, type);

    if (!mboxFile.exists()) {
      return Optional.empty();
    }

    return Optional.of(mboxFile);
  }
}
