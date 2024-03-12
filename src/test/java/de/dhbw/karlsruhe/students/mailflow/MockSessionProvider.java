package de.dhbw.karlsruhe.students.mailflow;

import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.MboxParser.SessionProvider;
import jakarta.mail.Authenticator;
import jakarta.mail.Session;
import java.util.Properties;

public class MockSessionProvider implements SessionProvider {

  @Override
  public Session getInstance(Properties properties, Authenticator authenticator) {
    return Session.getInstance(properties,authenticator);
  }
}
