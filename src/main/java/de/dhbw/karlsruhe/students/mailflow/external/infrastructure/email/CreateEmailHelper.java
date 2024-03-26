package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.email.EmailParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import java.io.IOException;

public final class CreateEmailHelper {

  private CreateEmailHelper() {}

  public static Email createEmailWithMessage(Message message) throws EmailParsingException {
    try {
      return Email.create(message.getContent().toString(),
          EmailMetadataFactory.withMessage(message).build(), null);
    } catch (IOException | MessagingException e) {
      throw new EmailParsingException("couldn't build final email", e);
    }
  }

}
