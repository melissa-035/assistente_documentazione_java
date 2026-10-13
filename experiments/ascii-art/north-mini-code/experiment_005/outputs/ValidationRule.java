```java
package pl.mgodyn.asciiart.application.validator;

/**
 * Defines a contract for validating input arguments within the ASCII Art application.
 * Implementing classes must check specific validation criteria (such as argument count,
 * formatting, or constraints) and provide a mechanism to determine validity
 * and report errors.
 */
public interface ValidationRule {

    /**
     * Validates the provided string arguments based on the specific rule implementation.
     *
     * @param args the array of string arguments to be validated
     * @return true if the arguments satisfy the validation rule, false otherwise
     */
    boolean isValid(String[] args);

    /**
     * Retrieves the error message describing why the validation failed.
     * <p>
     * This method should generally be invoked when {@link #isValid(String[]) isValid}
     * returns {@code false} to provide user feedback.
     *
     * @return a descriptive error message if validation fails, or an empty/default message otherwise
     */
    String getErrorMessage();
}
```