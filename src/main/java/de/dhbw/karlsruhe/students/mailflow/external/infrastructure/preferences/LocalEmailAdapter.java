package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import java.io.IOException;

/**
 * Adapter for Gson to handle Email objects during serialization.
 * The write method converts an Email object into a JSON string.
 * The read method is not implemented.
 */
public class LocalEmailAdapter extends TypeAdapter<Email> {

  @Override
  public void write(JsonWriter jsonWriter, Email email) throws IOException {
    jsonWriter.beginObject();
    jsonWriter.name("sender").value(email.getSender().toString());
    jsonWriter.name("subject").value(email.getSubject().toString());
    jsonWriter.name("content").value(email.getContent());
    jsonWriter.name("date").value(email.getSendDate().toString());
    jsonWriter.endObject();
  }

  @Override
  public Email read(JsonReader jsonReader) throws IOException {
    return null;
  }
}
