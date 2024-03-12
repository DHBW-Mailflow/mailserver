package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.MboxParser.FolderProvider;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.URLName;

public class MockFolderProvider implements FolderProvider {

  @Override
  public Folder getFolder(URLName urlName) throws MessagingException {
    return
  }
}
