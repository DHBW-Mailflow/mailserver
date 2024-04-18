package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * @author seiferla , Jonas-Karl

 */
public class HelperParsing {
  public static final String DATE_FORMAT = "yyyy-MM-dd";

  private HelperParsing() {
    throw new IllegalStateException("Utility class");
  }

  static LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return LocalDate.parse(date, formatter);
  }
}
