package de.dhbw.karlsruhe.students.mailflow.core.application.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.util.Optional;

/**
 * @author seiferla
 */
public interface MailboxFileProvider {
    Optional<File> provideStoredMailboxFileFor(Address userAddress, MailboxType type);

}
