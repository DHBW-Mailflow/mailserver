package de.dhbw.karlsruhe.students.mailflow.core.application.job;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class WorkerQueue {
  private static WorkerQueue INSTANCE;

  private PriorityQueue<Job> queue;

  private WorkerQueue() {
    this.queue = new PriorityQueue<>(new Comparator<Job>() {
      @Override
      public int compare(Job o1, Job o2) {
        return o1.getReadyTime().compareTo(o2.getReadyTime());
      }
    });
  }

  public static WorkerQueue getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new WorkerQueue();
    }

    return INSTANCE;
  }

  public boolean jobsAreDue() {
    return isReadyForExecution(queue.peek());
  }

  public void performDueJobs() {
    while (jobsAreDue()) {
      Job job = queue.poll();

      job.perform();
    }
  }

  public void enqueueIn(Job job) {
    this.queue.add(job);
  }

  private boolean isReadyForExecution(Job job) {
    if (job == null) {
      return false;
    }

    ZonedDateTime now = ZonedDateTime.now();
    return job.getReadyTime().isBefore(now);
  }
}
