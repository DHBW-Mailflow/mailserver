package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

/**
 * Representation of a custom e-mail header, consisting of a name and a value
 */
public record Header(String name, String value) {

  @Override
  public String toString() {
    return name + ": " + value;
  }

}