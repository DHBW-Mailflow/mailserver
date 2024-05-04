package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxExportRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.utils.FileHelper;
import java.io.File;
import java.time.LocalDateTime;

public class ExportFileRepository implements MailboxExportRepository {

  private final Gson gson;

  public ExportFileRepository() {
    this.gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Email.class, new LocalEmailAdapter())
            .create();
  }

  @Override
  public File exportMailbox(ExportableMailbox mailbox) throws ExportMailboxException {
    File file = new File(mailbox.type() + "_" + mailbox.owner() + ".json");
    try {
      FileHelper fileHelper = new FileHelper();
      fileHelper.saveToFile(file, gson.toJson(mailbox));
      System.out.println("Exported mailbox to " + file.getAbsolutePath());
    } catch (Exception e) {
      throw new ExportMailboxException("Could not export mailbox", e);
    }
    return file;
  }
}
