package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public record User(String email, String password) {

  public User {
    if (email == null || email.isBlank()) {
      throw new UserException("Email must not be null or empty");
    }
    if (password == null || password.isBlank()) {
      throw new UserException("Password must not be null or empty");
    }
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean checkPassword(String password) {
    return this.password.equals(password);
  }

  public boolean checkEmail(String email) {
    return this.email.equals(email);
  }

  public boolean checkUser(String email, String password) {
    return checkEmail(email) && checkPassword(password);
  }


}
