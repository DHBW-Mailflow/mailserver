package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Jonas-Karl, seiferla
 */
public class JSONMailboxConverter implements MailboxConverter {
  private final Gson gson = getMailboxGson();

  @Override
  public String serializeMailbox(Mailbox mailbox) {
    return gson.toJson(mailbox);
  }

  @Override
  public Mailbox deserializeMailboxFile(File mailboxFile) throws MailboxLoadingException {
    try (FileReader reader = new FileReader(mailboxFile)) {
      Mailbox mailbox = gson.fromJson(reader, Mailbox.class);
      if (mailbox == null) {
        String errorMessage =
            String.format("Mailbox could not be parsed from file %s", mailboxFile.getPath());
        throw new MailboxLoadingException(errorMessage);
      }
      return mailbox;
    } catch (JsonSyntaxException | JsonIOException | IOException e) {
      throw new MailboxLoadingException("Could not deserialize mailbox", e);
    }
  }

  private Gson getMailboxGson() {
    return new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeFormatter())
        .enableComplexMapKeySerialization()
        .create();
  }

  private static class LocalDateTimeFormatter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(JsonWriter out, LocalDateTime dateTime) throws IOException {
      out.value(dateTime.toString());
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
        return null;
      }
      return LocalDateTime.parse(in.nextString());
    }
  }
}
