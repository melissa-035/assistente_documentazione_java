```
/**
 * Represents a validation rule for application input arguments.
 *
 * <p>This interface defines the contract for validating a set of arguments
 * (typically represented as a String array) and retrieving a descriptive
 * error message if the validation fails. It is designed to be implemented
 * for various input validation scenarios within the application.</p>
 */
package pl.mgodyn.asciiart.application.validator;

public interface ValidationRule {

    /**
     * Validates the provided input arguments.
     *
     * @param args the array of arguments to be validated.
     * @return {@code true} if the arguments are valid, {@code false} otherwise.
     */
    boolean isValid(String[] args);

    /**
     * Retrieves the error message associated with the validation failure.
     *
     * @return a String describing the validation error.
     */
    String getErrorMessage();
}
```