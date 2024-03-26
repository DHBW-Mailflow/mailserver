package de.dhbw.karlsruhe.students.mailflow.core.application.auth;

public interface AuthorizationService {
  public boolean authorize(String email, String password);
}
