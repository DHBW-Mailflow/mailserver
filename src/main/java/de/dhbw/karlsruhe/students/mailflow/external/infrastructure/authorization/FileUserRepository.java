package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveUserException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

  public FileUserRepository() {
    this.gson = new Gson();
    this.users = new HashSet<>();
    this.filePath = new File("users.json");
  }

  @VisibleForTesting
  public FileUserRepository(File file) {
    Logger.getLogger(FileUserRepository.class.getName()).warning("VisibleForTesting method called");
    this.filePath = file;
    this.gson = new Gson();
    this.users = new HashSet<>();
  }

  /** Loads the users from the file */
  private void loadUsers() throws LoadingUsersException {
    users.clear();
    createFileIfNotExists();
    try (FileReader reader = new FileReader(filePath)) {
      Type setType = new TypeToken<HashSet<User>>() {}.getType();
      HashSet<User> parsedUsers = gson.fromJson(reader, setType);
      if (parsedUsers == null) {
        return;
      }
      users.addAll(parsedUsers);
    } catch (IOException | JsonIOException | JsonSyntaxException e) {
      throw new LoadingUsersException("Could not load users", e);
    }
  }

  private void createFileIfNotExists() throws LoadingUsersException {
    try {
      // creates the file if it does not already exist
      if (!filePath.createNewFile()) {
        throw new LoadingUsersException("Could not create user file");
      }
    } catch (IOException | SecurityException e) {
      throw new LoadingUsersException("Could not create user file", e);
    }
  }

  @Override
  public Optional<User> findByEmail(Address email) throws LoadingUsersException {
    loadUsers();
    return users.stream().filter(user -> user.email().equals(email)).findFirst();
  }

  @Override
  public boolean save(User user) throws SaveUserException, LoadingUsersException {
    loadUsers();
    users.add(user);
    try (FileWriter writer = new FileWriter(filePath)) {
      gson.toJson(users, writer);
      return true;
    } catch (IOException e) {
      throw new SaveUserException("Could not save user", e);
    }
  }
}
