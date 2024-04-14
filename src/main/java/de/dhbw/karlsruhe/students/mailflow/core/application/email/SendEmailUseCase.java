package de.dhbw.karlsruhe.students.mailflow.core.application.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public interface SendEmailUseCase {
    Email prepareEmail(Address sender, String subject, String content, String recipientTo, String recipientsCC, String recipientsBCC);

    void send(Email email);
}
