package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A repository for users that stores them in a file
 */

public class FileUserRepository implements UserRepository{

  private final String filePath;
  private final Gson gson;

  private final Set<User> users;

  /**
   * @param filePath the path to the file where the users are store
   */

  public FileUserRepository(String filePath) {
    this.filePath = filePath;
    this.gson = new Gson();
    this.users = loadUsers();
  }
  /**
   * Finds a user by email and password
   */
  @Override
  public Optional<User> findByEmailAndPassword(Address email, String password) {
    return users.stream()
        .filter(user -> user.email().equals(email) && user.password().equals(password))
        .findFirst();
  }

  /**
   * Registers a user
   */
  public void registerUser(User user) throws SaveUserException {
    String hashedPassword = hashPassword(user.password());
    User userWithHashedPassword = new User(user.email(), hashedPassword);
    users.add(userWithHashedPassword);
    saveUsers();
  }

  private String hashPassword(String password) {

    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(password.getBytes());
      BigInteger number = new BigInteger(1, hash);
      StringBuilder hexString = new StringBuilder(number.toString(16));
      while (hexString.length() < 32) {
        hexString.insert(0, '0');
      }
      return hexString.toString();
    }catch (NoSuchAlgorithmException e){
      throw new RuntimeException("Could not hash password", e);
    }

  }

  /**
   * Loads the users from the file
   */
  private Set<User> loadUsers() {
    try (FileReader reader = new FileReader(filePath)) {
      Type setType = new TypeToken<HashSet<User>>(){}.getType();
      return gson.fromJson(reader, setType);
    } catch (IOException e) {
      return new HashSet<>();
    }
  }

  /**
   * Saves the users to the file
   */

  private void saveUsers() throws SaveUserException {
    try (FileWriter writer = new FileWriter(filePath)) {
      gson.toJson(users, writer);
    } catch (IOException e) {
      throw new SaveUserException("Could not save users", e);
    }
  }

}
