package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

/**
 * Validates the length of the provided arguments. This rule ensures that
 * exactly one argument is present. If the number of arguments is not equal
 * to one, the validation fails.
 *
 * @see ValidationRule
 */
public class LengthValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "Too many or no arguments!";

    @Override
    public boolean isValid(String[] args) {
        return args.length == 1;
    }

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}