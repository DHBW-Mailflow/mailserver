package de.dhbw.karlsruhe.students.mailflow.core.application.job;

import java.time.ZonedDateTime;

public interface Job {
  /**
   * @return the time when this job is ready to perform its task
   */
  ZonedDateTime getReadyTime();

  /**
   * performs the job's task
   */
  void perform() throws JobExecutionException;
}
