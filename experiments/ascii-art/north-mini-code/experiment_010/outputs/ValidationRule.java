```java
package pl.mgodyn.asciiart.application.validator;

/**
 * Defines a contract for validating input arguments.
 * Implementations of this rule check if a specific set of conditions
 * are met for the provided arguments and provide feedback on failures.
 * This interface is typically used to ensure that application inputs (such as
 * command-line arguments) adhere to required formats or constraints before processing.
 */
public interface ValidationRule {

    /**
     * Validates the provided arguments against the specific rule criteria.
     *
     * @param args An array of strings representing the inputs to be validated.
     *             This typically includes command-line arguments or user inputs.
     * @return {@code true} if the arguments satisfy the validation criteria,
     *         {@code false} otherwise.
     */
    boolean isValid(String[] args);

    /**
     * Provides a descriptive error message explaining why the validation failed.
     *
     * @return A human-readable string describing the validation failure reason.
     *         This method is intended to be used after {@link #isValid(String[]) isValid}
     *         returns {@code false} to inform the user of specific issues.
     */
    String getErrorMessage();
}
```