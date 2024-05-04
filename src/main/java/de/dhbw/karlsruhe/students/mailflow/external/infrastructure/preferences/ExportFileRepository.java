package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxExportRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.utils.FileHelper;
import java.io.File;
import java.time.LocalDateTime;

public class ExportFileRepository implements MailboxExportRepository {

  private final Gson gson;

  private static final String EXPORTS_DIRECTORY = "exports/";

  public ExportFileRepository() {
    this.gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
  }

  @Override
  public File exportMailbox(ExportableMailbox mailbox) throws ExportMailboxException {
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
    return file;
  }

  private String parseExportedDate(LocalDateTime localDateTime) {
    return "%s-%s-%s"
        .formatted(
            localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
  }
}
