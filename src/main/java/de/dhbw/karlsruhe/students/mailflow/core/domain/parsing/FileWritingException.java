package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing;

public class FileWritingException extends Exception {

  public FileWritingException(String s, Exception exception) {
    super(s, exception);
  }
}
