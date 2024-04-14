package de.dhbw.karlsruhe.students.mailflow.core.application.email.provideUnreadMails;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import java.util.List;

public interface ProvideUnreadEmailsUseCase {

List<Email> provideUnreadEmails();

}
