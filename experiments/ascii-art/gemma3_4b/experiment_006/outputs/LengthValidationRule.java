```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

public class LengthValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "Too many or no arguments!";

    /**
     * Validates if the input array has exactly one element.
     *
     * @param args The input array to validate.
     * @return True if the array has exactly one element, false otherwise.
     * @see ValidationRule
     */
    @Override
    public boolean isValid(String[] args) {
        return args.length == 1;
    }

    /**
     * Returns the error message associated with this validation rule.
     *
     * @return The error message.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```