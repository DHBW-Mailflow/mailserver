package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization;

import de.dhbw.karlsruhe.students.mailflow.core.domain.auth.HashingFailedException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author seiferla
 */
public class PasswordHasher {

  private static final int SALT_LENGTH = 16;
  private static final SecureRandom secureRandom = new SecureRandom();

  private PasswordHasher() {}

  /**
   * This computes an horrifying insecure hash value.
   *
   * DO NOT USE IN PRODUCTION!
   *
   * You should actually use argon2id or something that is actually recommended to be used. However,
   * we may not use other libraries simply, so let's stick with this before trying to get an
   * approval. :D
   */
  public static String hashPassword(String password, String salt) throws HashingFailedException {
    return generateHash(password + salt);
  }

  public static String generateSalt() {
    byte[] salt = new byte[SALT_LENGTH];
    secureRandom.nextBytes(salt);
    return new String(salt);
  }

  private static String generateHash(String input) throws HashingFailedException {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(input.getBytes());
      BigInteger number = new BigInteger(1, hash);
      StringBuilder hexString = new StringBuilder(number.toString(16));
      while (hexString.length() < 32) {
        hexString.insert(0, '0');
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new HashingFailedException("Could not hash password", e);
    }
  }
}
