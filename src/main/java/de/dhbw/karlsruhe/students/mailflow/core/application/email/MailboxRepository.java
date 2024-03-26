package de.dhbw.karlsruhe.students.mailflow.core.application.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import java.io.File;
import java.util.Optional;

public interface MailboxRepository {
  Optional<File> provideStoredMailboxFileFor(User user, MailboxType type);

}
