```java
package pl.mgodyn.asciiart.application.validator;

import pl.mgodyn.asciiart.application.validator.rules.FileExistenceValidationRule;
import pl.mgodyn.asciiart.application.validator.rules.FileExtensionValidationRule;
import pl.mgodyn.asciiart.application.validator.rules.LengthValidationRule;

import java.util.List;

/**
 * A chain of validation rules for ASCII art files. This class provides a simple
 * mechanism to sequentially validate an array of arguments (likely filenames)
 * against a series of criteria.
 */
public final class AsciiArtValidationChain {

    private List<ValidationRule> rules;

    private AsciiArtValidationChain() {
        rules = List.of(
                new LengthValidationRule(),
                new FileExistenceValidationRule(),
                new FileExtensionValidationRule()
        );
    }

    /**
     * Validates the provided arguments against the defined validation rules.
     *
     * @param args An array of strings representing the arguments to be validated.
     *             Typically filenames.
     * @return True if all validation rules pass, false otherwise.  If any rule fails,
     *         an error message is printed to `System.err` and the method returns false immediately.
     */
    public static boolean validateAndHandleErrors(String[] args) {
        AsciiArtValidationChain validationChain = new AsciiArtValidationChain();
        return validationChain.validate(args);
    }

    /**
     * Validates the given arguments against each rule in the validation chain.
     *
     * @param args An array of strings representing the arguments to be validated.
     *             Typically filenames.
     * @return True if all validation rules pass, false otherwise.
     */
    private boolean validate(String[] args) {
        for (ValidationRule rule : rules) {
            if (!rule.isValid(args)) {
                System.err.println(rule.getErrorMessage());
                return false;
            }
        }
        return true;
    }
}
```