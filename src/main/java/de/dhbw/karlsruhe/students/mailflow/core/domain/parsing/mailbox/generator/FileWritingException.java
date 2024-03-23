package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.generator;

public class FileWritingException extends Exception {

  public FileWritingException(String s, Exception exception) {
    super(s, exception);
  }
}
