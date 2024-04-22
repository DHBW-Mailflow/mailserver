package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureService;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.SaveSettingsException;
import org.junit.jupiter.api.Test;

class ChangeSignatureTest {

  UserSettingsRepository mockUserSettingsRepository =
      new UserSettingsRepository() {
        @Override
        public void updateUserSettings(UserSettings userSettings) {}

        @Override
        public UserSettings getSettings(Address address) {
          Address testAddress = new Address("test", "example.com");
          return new UserSettings(testAddress, "Test Signature");
        }
      };

  AuthSessionUseCase mockAuthSession =
      new AuthSessionUseCase() {
        @Override
        public boolean isLoggedIn() {
          return true;
        }

        @Override
        public Address getSessionUserAddress() throws IllegalStateException {
          return new Address("test", "example.com");
        }

        @Override
        public void ensureLoggedIn() {}

        @Override
        public void removeSessionUser() {}

        @Override
        public void setSessionUser(User user) {}
      };

  ChangeSignatureService changeSignatureService =
      new ChangeSignatureService(mockUserSettingsRepository, mockAuthSession);

  @Test
  void resetSignature() {
    assertDoesNotThrow(() -> changeSignatureService.resetSignature());
  }

  @Test
  void getSignature() throws LoadSettingsException, SaveSettingsException {
    String expectedSignature = "Test Signature";
    assertEquals(expectedSignature, changeSignatureService.getSignature());
  }
}
