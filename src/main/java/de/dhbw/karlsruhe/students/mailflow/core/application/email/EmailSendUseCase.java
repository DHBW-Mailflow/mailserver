package de.dhbw.karlsruhe.students.mailflow.core.application.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;

public interface EmailSendUseCase {
    public void deliverEmail(Email email);
}
