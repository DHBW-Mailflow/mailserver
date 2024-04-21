package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;


public class SignatureBuilder {

  private String signature;

  public SignatureBuilder signature(String signature) {
    this.signature = signature;
    return this;
  }

  public String build() {
    return this.signature;
  }

}
