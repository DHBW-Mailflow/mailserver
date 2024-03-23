package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.creator;

public class FileWritingException extends Exception {

  public FileWritingException(String s, Exception exception) {
    super(s, exception);
  }
}
