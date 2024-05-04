package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Adapter for Gson to handle LocalDateTime objects during serialization and deserialization. The
 * write method converts a LocalDateTime object into a JSON string. The read method converts a JSON
 * string into a LocalDateTime object.
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

  @Override
  public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
    if (localDateTime == null) {
      jsonWriter.nullValue();
    } else {
      jsonWriter.value(localDateTime.toString());
    }
  }

  @Override
  public LocalDateTime read(JsonReader jsonReader) throws IOException {
    if (jsonReader != null) {
      return LocalDateTime.parse(jsonReader.nextString());
    } else {
      return null;
    }
  }
}
