package de.dhbw.karlsruhe.students.mailflow.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.JSONMailboxConverter;

/**
 * @author jens1o
 */
public class FileMailboxRepositoryIntegrationTest {
    private File allMailboxesDirectory;

    @BeforeEach
    void setUp(@TempDir File tempDir) {
        allMailboxesDirectory = tempDir;
    }

    @ParameterizedTest(name = "should create directories and files for mailboxType {0}")
    @EnumSource(MailboxType.class)
    /**
     * #BugFix
     */
    void searchingNonExistentMailboxShouldCreateParsableAndEmptyMailbox(MailboxType mailboxType)
            throws MailboxSavingException, MailboxLoadingException {
        // Arrange
        Address mailboxOwner = new Address("someOwner", "someDomain.de");
        Mailbox searchingMailbox = Mailbox.create(mailboxOwner, Map.of(), mailboxType);
        File directoryOfUser = new File(allMailboxesDirectory, mailboxOwner.toString());
        File mailboxFile = new File(directoryOfUser, mailboxType.getStoringName() + ".json");

        FileMailboxRepository fileMailboxRepository =
                new FileMailboxRepository(new JSONMailboxConverter(), allMailboxesDirectory);

        // Act
        Mailbox foundMailbox =
                fileMailboxRepository.findByAddressAndType(mailboxOwner, mailboxType);

        // Assert
        assertTrue(mailboxFile.exists());

        assertEquals(foundMailbox.getOwner(), searchingMailbox.getOwner());
        assertEquals(foundMailbox.getEmailList(), searchingMailbox.getEmailList());
        assertTrue(foundMailbox.getEmailList().isEmpty());
    }
}
