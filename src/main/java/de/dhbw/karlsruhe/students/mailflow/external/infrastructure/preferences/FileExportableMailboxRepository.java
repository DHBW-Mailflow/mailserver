package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.ExportableMailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.ExportMailboxException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.utils.FileHelper;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * @author seiferla
 */
public class FileExportableMailboxRepository implements ExportableMailboxRepository {

  private final Gson gson;

  private static final String EXPORTS_DIRECTORY = "exports/";

  public FileExportableMailboxRepository() {
    this.gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
  }

  @Override
  public void saveMailbox(ExportableMailbox mailbox) throws ExportMailboxException {
    File file =
        new File(
            EXPORTS_DIRECTORY,
            mailbox.ownerAddress()
                + "_"
                + parseExportedDate(mailbox.exportedDate())
                + "_"
                + mailbox.mailBoxType()
                + ".json");
    try {
      FileHelper fileHelper = new FileHelper();
      fileHelper.saveToFile(file, gson.toJson(mailbox));
    } catch (Exception e) {
      throw new ExportMailboxException("Could not export mailbox", e);
    }
  }

  private String parseExportedDate(LocalDateTime localDateTime) {
    final DateTimeFormatter formatter =
        new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd_HH-mm-ss")
            .toFormatter();
    return localDateTime.format(formatter);
  }
}
