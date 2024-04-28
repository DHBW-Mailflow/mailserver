package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

/**
 * Representation of a custom e-mail header, consisting of a name and a value
 *
 * @author jens1o
 */
public record Header(String key, String... values) {

  @Override
  public String toString() {
    if (values.length == 0) {
      return "";
    }

    StringBuilder result = new StringBuilder();
    result.append(key).append(": ");
    for (String value : values) {
      result.append("<%s> ".formatted(value));
    }
    return result.toString().trim();
  }
}
