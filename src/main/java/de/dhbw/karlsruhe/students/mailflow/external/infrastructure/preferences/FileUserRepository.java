package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.LoadingUsersException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.FileHelper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import org.fest.util.VisibleForTesting;

/**
 * @author seiferla
 */
public class FileUserRepository implements UserRepository {

  private final Gson gson;

  private final Set<User> users;

  private final File filePath;

  private final FileHelper fileHelper;
  private String defaultFileContent;
  private final Type setType = new TypeToken<HashSet<User>>() {}.getType();

  public FileUserRepository() {
    this.gson = new Gson();
    this.users = new HashSet<>();
    this.filePath = new File("users.json");
    this.fileHelper = new FileHelper();
  }

  @VisibleForTesting
  public FileUserRepository(File file) {
    Logger.getLogger(FileUserRepository.class.getName()).warning("VisibleForTesting method called");
    this.filePath = file;
    this.gson = new Gson();
    this.users = new HashSet<>();
    this.fileHelper = new FileHelper();
  }

  private void initFile() throws IOException {
    if (filePath.exists()) {
      return;
    }
    Set<User> defaultUsers = Set.of();
    defaultFileContent = gson.toJson(defaultUsers);
    fileHelper.saveToFile(filePath, defaultFileContent);
  }

  /** Loads the users from the file */
  private void loadUsers() throws LoadingUsersException, SaveUserException {
    users.clear();

    try {
      initFile();
    } catch (IOException e) {
      throw new SaveUserException("Could not initialize file", e);
    }

    try {
      final String json = fileHelper.readFileContent(filePath, defaultFileContent);
      final HashSet<User> parsedUsers = gson.fromJson(json, setType);
      if (parsedUsers == null) {
        return;
      }
      users.addAll(parsedUsers);
    } catch (IOException | JsonIOException | JsonSyntaxException e) {
      throw new LoadingUsersException("Could not load users", e);
    }
  }

  @Override
  public Optional<User> findByEmail(Address email) throws LoadingUsersException, SaveUserException {
    loadUsers();
    return users.stream().filter(user -> user.hasEmail(email)).findFirst();
  }

  @Override
  public boolean save(User user) throws SaveUserException, LoadingUsersException {
    loadUsers();
    users.add(user);
    try {
      fileHelper.saveToFile(filePath, gson.toJson(users));
      return true;
    } catch (IOException e) {
      throw new SaveUserException("Could not save user", e);
    }
  }
}
