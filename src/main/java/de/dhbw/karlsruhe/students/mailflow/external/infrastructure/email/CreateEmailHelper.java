package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.EmailParsingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.EmailBuilder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import java.io.IOException;

/**
 * @author seiferla, Jonas-Karl
 */
public final class CreateEmailHelper {

  private CreateEmailHelper() {}

  public static Email createEmailWithMessage(Message message) throws EmailParsingException {

    try {
      return new EmailBuilder()
          .withContent(message.getContent().toString())
          .withMetaData(EmailMetadataFactory.withMessage(message).build())
          .build();
    } catch (IOException | MessagingException e) {
      throw new EmailParsingException("couldn't build final email", e);
    }
  }
}
