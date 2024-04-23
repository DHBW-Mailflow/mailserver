package de.dhbw.karlsruhe.students.mailflow.core.application.email.spam.strategies;

import java.util.regex.Pattern;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;

/**
 * @author jens1o
 */
public class ContentAnalysisSpamDetectionStrategy implements SpamDetectionStrategy {

  @Override
  public boolean isSpam(MailboxRepository mailboxRepository, Email email) {
    return getBlockedWordsPattern().matcher(email.getContent()).find();

  }

  private Pattern getBlockedWordsPattern() {
    return Pattern.compile("(?<!\\S)beleidigung(?!\\S)",
        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
  }

  @Override
  public String getHumanReadableReason() {
    return "e-mail content has blocked words";
  }

}
