```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

public class LengthValidationRule implements ValidationRule {

    private static final String ERROR_MESSAGE = "Too many or no arguments!";

    /**
     * Checks if the provided arguments array has a length of exactly 1.
     *
     * @param args The arguments array to validate.
     * @return True if the array has a length of 1, false otherwise.
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