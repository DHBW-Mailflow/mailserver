package de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums;

public enum HeaderKey {
  MESSAGE_ID("Message-ID"),
  IN_REPLY_TO("In-Reply-To"),
  REFERENCES("References");

  private final String readableKeyName;

  HeaderKey(String readableKeyName) {
    this.readableKeyName = readableKeyName;
  }

  public String getKeyName() {
    return readableKeyName;
  }
}
