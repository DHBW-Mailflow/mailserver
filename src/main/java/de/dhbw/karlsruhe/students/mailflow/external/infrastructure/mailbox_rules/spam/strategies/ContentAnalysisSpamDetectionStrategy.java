package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam.strategies;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.SpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import java.util.regex.Pattern;

/**
 * @author jens1o
 */
public class ContentAnalysisSpamDetectionStrategy implements SpamDetectionStrategy {

  @Override
  public boolean isSpam(Email email) {
    return getBlockedWordsPattern().matcher(email.getContent()).find();
  }

  private Pattern getBlockedWordsPattern() {
    return Pattern.compile(
        "(?<!\\S)beleidigung(?!\\S)",
        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
  }

  @Override
  public String getReason() {
    return "e-mail content has blocked words";
  }
}
