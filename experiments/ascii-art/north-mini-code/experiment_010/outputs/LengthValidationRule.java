```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

/**
 * This validation rule checks the length of the input arguments.
 * It ensures that the provided argument array contains exactly one element,
 * which is necessary for the core ASCII art generation operation.
 * If the argument count is not equal to 1, this rule reports a validation failure.
 */
public class LengthValidationRule implements ValidationRule {

    /**
     * The error message returned when the validation fails.
     * This indicates that the user has provided either too few arguments
     * (missing input) or too many arguments (excess parameters).
     */
    private final String ERROR_MESSAGE = "Too many or no arguments!";

    /**
     * Validates the input arguments based on the required length.
     * The rule is considered valid only if the argument array contains exactly one element.
     *
     * @param args The array of input arguments to be validated.
     * @return {@code true} if {@code args.length == 1}, {@code false} otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        return args.length == 1;
    }

    /**
     * Retrieves the error message specific to this validation rule.
     *
     * @return The error message string: "Too many or no arguments!".
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```