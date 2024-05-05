package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Objects;

/**
 * Representation of the e-mail date
 *
 * @author Jonas-Karl
 */
public final class SentDate {
  private final LocalDateTime date;

  /** */
  public SentDate(LocalDateTime date) {
    this.date = date;
  }

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
    DateTimeFormatter formatter =
        new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm").toFormatter();
    return date.format(formatter);
  }

  public LocalDateTime date() {
    return date;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (SentDate) obj;
    return Objects.equals(this.date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date);
  }

  @Override
  public String toString() {
    return "SentDate[" + "date=" + date + ']';
  }
}
