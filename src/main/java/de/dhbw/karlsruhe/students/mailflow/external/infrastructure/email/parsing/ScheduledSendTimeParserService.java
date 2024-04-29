package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author jens1o
 */
public class ScheduledSendTimeParserService implements ScheduledSendTimeParserUseCase {

  public ZonedDateTime parseScheduledSendDateTime(String userInput) {
    DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault());
    ZonedDateTime scheduledDate = ZonedDateTime.now();

    try {
      scheduledDate = formatter.parse(userInput, ZonedDateTime::from);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "Syntax error while parsing the scheduled time specified, please try again.");
    }

    if (scheduledDate.isBefore(ZonedDateTime.now())) {
      throw new IllegalArgumentException(
          "Please make sure that the scheduled time is in the future.");
    }

    return scheduledDate;
  }

}
