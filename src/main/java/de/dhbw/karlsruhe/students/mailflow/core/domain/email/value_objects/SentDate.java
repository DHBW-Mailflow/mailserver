package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * Representation of the e-mail date
 *
 * @author Jonas-Karl
 */
public record SentDate(LocalDateTime date) {
  /**
   * @param date string of a specific format. For example 2024-03-20T11:02:50.00Z
   * @return a new instance of SentDate with the parsed date
   */
  public static SentDate ofFormattedString(String date) {
    return new SentDate(LocalDateTime.ofInstant(Instant.parse(date), ZoneOffset.UTC));
  }

  public static SentDate ofNow() {
    return new SentDate(LocalDateTime.now());
  }

  public static SentDate ofDate(Date sentDate) {
    Instant instant = sentDate.toInstant();
    return new SentDate(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
  }

  public String formattedDate() {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm")
        .toFormatter();
    return date.format(formatter);
  }
}
