```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

/**
 * Validates the file extension to ensure it is one of the supported types (PNG, JPG, JPEG).
 * <p>
 * This rule checks the first argument of the application (the file path) and returns
 * true if the extension matches .png, .jpg, or .jpeg (case-insensitive).
 * </p>
 *
 * @see ValidationRule
 */
public class FileExtensionValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Validates that the file name has an allowed extension.
     *
     * @param args the command line arguments where the first element is the file name.
     * @return true if the file name ends with .png, .jpg, or .jpeg (case-insensitive), false otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    /**
     * Returns the error message to be displayed when validation fails.
     *
     * @return the error message string.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```