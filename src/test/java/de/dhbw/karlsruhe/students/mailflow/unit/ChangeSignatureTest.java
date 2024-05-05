package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.UserSettingsRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;
import org.junit.jupiter.api.Test;

class ChangeSignatureTest {

  Address testAddress = new Address("test", "example.com");

  UserSettingsRepository mockUserSettingsRepository = new MockedUserSettingsRepository(testAddress);

  AuthSessionUseCase mockAuthSession = new MockedAuthorizedSession(testAddress);

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
