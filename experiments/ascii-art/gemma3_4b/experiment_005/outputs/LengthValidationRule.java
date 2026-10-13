```java
package pl.mgodyn.asciiart.application.validator.rules;

import java.util.Arrays;

/**
 * This class implements a validation rule that checks if the input array has exactly one element.
 * It is part of the application's validator rules module.
 */
public class LengthValidationRule implements ValidationRule {

    /**
     * The error message to return if the validation rule fails.
     * Defaults to "Too many or no arguments!".
     */
    private final String ERROR_MESSAGE = "Too many or no arguments!";

    /**
     * Checks if the input array has exactly one element.
     *
     * @param args The input array of strings to validate.
     * @return {@code true} if the array has exactly one element, {@code false} otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        return args.length == 1;
    }

    /**
     * Returns the error message to be displayed if the validation rule fails.
     *
     * @return The error message.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```