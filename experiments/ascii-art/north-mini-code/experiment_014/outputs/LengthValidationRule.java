```java
/**
 * A validation rule that checks the length of input arguments for the ASCII Art application.
 * <p>
 * This rule implements the {@link ValidationRule} interface and enforces the constraint
 * that the input array must contain exactly one argument. If the array contains zero
 * arguments or more than one argument, the validation fails.
 * </p>
 *
 * @see ValidationRule
 */
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

public class LengthValidationRule implements ValidationRule {

    /**
     * The error message returned when the argument length validation fails.
     * <p>
     * This occurs when the input arguments are either empty or exceed the expected
     * count of a single argument.
     * </p>
     */
    private final String ERROR_MESSAGE = "Too many or no arguments!";

    /**
     * Validates the input arguments based on their length.
     * <p>
     * This method checks if the provided array of arguments contains exactly one element.
     * It returns {@code true} if the length is 1, otherwise it returns {@code false}.
     * </p>
     *
     * @param args The array of string arguments to validate.
     * @return {@code true} if the number of arguments is exactly one; {@code false} otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        return args.length == 1;
    }

    /**
     * Retrieves the specific error message associated with this validation rule.
     * <p>
     * This message is used to inform the user of the reason why validation failed
     * when {@link #isValid(String[])} returns {@code false}.
     * </p>
     *
     * @return The error message string: "Too many or no arguments!".
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```