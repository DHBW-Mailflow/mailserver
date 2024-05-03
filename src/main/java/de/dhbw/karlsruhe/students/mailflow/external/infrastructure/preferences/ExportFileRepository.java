package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.Gson;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxExportRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.ExportableMailbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.utils.FileHelper;
import java.io.File;

public class ExportFileRepository implements MailboxExportRepository {

  private final Gson gson;

  public ExportFileRepository() {
    this.gson = new Gson();
  }

  @Override
  public File exportMailbox(ExportableMailbox mailbox) throws ExportMailboxException {
    File file = new File(mailbox.type() + "_" + mailbox.owner() + ".json");
    try {
      FileHelper fileHelper = new FileHelper();
      fileHelper.saveToFile(file, gson.toJson(mailbox));
    } catch (Exception e) {
      throw new ExportMailboxException("Could not export mailbox", e);
    }
    return file;
  }
}
