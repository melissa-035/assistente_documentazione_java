```java
package pl.mgodyn.asciiart.application.validator;

/**
 * Defines a validation rule for input arguments.
 * Implementations of this interface validate a set of string arguments and provide
 * an error message in case of failure.
 */
public interface ValidationRule {

    /**
     * Validates the provided arguments against the rule.
     *
     * @param args the array of string arguments to validate
     * @return {@code true} if the arguments are valid according to the rule, {@code false} otherwise
     */
    boolean isValid(String[] args);

    /**
     * Returns an error message describing the validation failure.
     * The message should be human-readable and explain why the validation rule failed.
     *
     * @return an error message string
     */
    String getErrorMessage();
}
```