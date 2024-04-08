package de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions;

import java.io.IOException;

/**
 * @author Jonas-Karl
 */
public class MailboxSavingException extends Exception {
  private static String format(String filePath) {
    return String.format("Could not create file %s", filePath);
  }

  public MailboxSavingException(String path) {
    super(format(path));
  }

  public MailboxSavingException(String path, IOException e) {
    super(format(path), e);
  }
}
