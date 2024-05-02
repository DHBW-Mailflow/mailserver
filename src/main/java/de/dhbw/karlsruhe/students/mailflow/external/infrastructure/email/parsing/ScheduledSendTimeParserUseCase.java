package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing;

import java.time.ZonedDateTime;

public interface ScheduledSendTimeParserUseCase {
  public ZonedDateTime parseScheduledSendDateTime(String userInput);
}
