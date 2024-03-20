package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class GSONConfiguration {

  public static Gson getConfiguredGson() {
    return new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeFormatter())
        .create();
  }

  static class LocalDateTimeFormatter extends TypeAdapter<LocalDateTime> {
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
