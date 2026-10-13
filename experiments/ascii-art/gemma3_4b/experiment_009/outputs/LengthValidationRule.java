```java
package pl.mgodyn.asciiart.application.validator.rules;

import java.util.Arrays;

/**
 * A validation rule that checks if the input array has exactly one element.
 * <p>
 * This rule is designed for scenarios where a validator expects a single argument
 * to perform a specific operation or validation.
 * </p>
 *
 * @author mgodyn
 */
public class LengthValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "Too many or no arguments!";

    /**
     * Validates the given array of arguments.
     *
     * @param args The array of arguments to validate.
     * @return {@code true} if the array has exactly one element, {@code false} otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        return args.length == 1;
    }

    /**
     * Returns the error message to be displayed if the validation fails.
     *
     * @return The error message.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```