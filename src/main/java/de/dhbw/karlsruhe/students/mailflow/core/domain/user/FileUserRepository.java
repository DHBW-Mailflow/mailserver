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
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 *
 */
public class FileUserRepository implements UserRepository{


  private final Gson gson;

  private static final SecureRandom secureRandom = new SecureRandom();

  private static final int SALT_LENGTH = 16;

  private final Set<User> users;

  private static final String FILE_PATH = "users.json";

  public FileUserRepository() {
    this.gson = new Gson();
    this.users = loadUsers();
  }
  /**
   * Finds a user by email and password
   */
  @Override
  public Optional<User> findByEmailAndPassword(Address email, String password){
    return users.stream()
        .filter(user -> {
          try {
            return user.email().equals(email) && user.password().equals(hashPassword(password, user.salt()));
          } catch (HashingFailedException e) {
            throw new UserAuthenticationException("Error while hashing password", e);
          }
        })
        .findFirst();
  }

  /** Registers a user */
  public void registerUser(Address email, String password) throws SaveUserException, HashingFailedException {

    if (users.stream().anyMatch(user -> user.email().equals(email))) {
      throw new SaveUserException("User is already registered");
    }

    String salt = generateSalt();
    String hashedPassword = hashPassword(password, salt);
    User userWithHashedPassword = new User(email, hashedPassword, salt);
    users.add(userWithHashedPassword);
    saveUsers();
  }

  private String hashPassword(String password, String salt) throws HashingFailedException {
    return generateHash(password + salt);
  }

  private String generateHash(String input) throws HashingFailedException {

    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(input.getBytes());
      BigInteger number = new BigInteger(1, hash);
      StringBuilder hexString = new StringBuilder(number.toString(16));
      while (hexString.length() < 32) {
        hexString.insert(0, '0');
      }
      return hexString.toString();
    }catch (NoSuchAlgorithmException e){
      throw new HashingFailedException("Could not hash password", e);
    }

  }
  /**
   * Generates a random salt
   * Tries to prevent rainbow table attacks
   */
  private String generateSalt() {
    byte[] salt = new byte[SALT_LENGTH];
    secureRandom.nextBytes(salt);
    return new String(salt);
  }

  /**
   * Loads the users from the file
   */
  private Set<User> loadUsers() {
    try (FileReader reader = new FileReader(FILE_PATH)) {
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
    try (FileWriter writer = new FileWriter(FILE_PATH)) {
      gson.toJson(users, writer);
    } catch (IOException e) {
      throw new SaveUserException("Could not save users", e);
    }
  }

  public void clearUsers() {
    users.clear();
  }

}
