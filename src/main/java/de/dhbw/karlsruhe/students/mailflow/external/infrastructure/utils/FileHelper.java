package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Jonas-Karl
 */
public class FileHelper {
  public String readFileContent(File filePath, String defaultContent) throws IOException {
    createInitialFileWithContent(filePath, defaultContent);
    return Files.readString(filePath.toPath(), StandardCharsets.UTF_8);
  }

  /**
   * @param filePath retrieving File
   * @param defaultContent default content to be written
   * @throws IOException when a error occurred during the creation process
   */
  private void createInitialFileWithContent(File filePath, String defaultContent)
      throws IOException {
    if (filePath.exists()) {
      return;
    }
    saveToFile(filePath, defaultContent);
  }

  public void saveToFile(File filePath, String content) throws IOException {
    this.createFile(filePath);
    this.writeContentToFile(filePath, content);
  }

  private void createFile(File filePath) throws IOException {
    if (filePath.exists()) {
      return;
    }

    if (filePath.getParentFile() == null) { // file to create is in root directory
      if (!filePath.createNewFile()) {
        throw new IOException("Could not create file at %s".formatted(filePath.getPath()));
      }
    } else if (!filePath.getParentFile().mkdirs()
        && !filePath.createNewFile()) { // nested directory
      throw new IOException("Could not create file at %s".formatted(filePath.getPath()));
    }
  }

  /**
   * overrides existing content of provided file
   *
   * @param filePath the file to write the content to
   * @param content the content to write
   * @throws IOException when an error occurred during the writing process
   */
  private void writeContentToFile(File filePath, String content) throws IOException {
    Files.writeString(filePath.toPath(), content);
  }

  public boolean existsFile(File mailboxFile) throws IOException {
    return mailboxFile.exists() && Files.readAllBytes(mailboxFile.toPath()).length != 0;
  }
}
