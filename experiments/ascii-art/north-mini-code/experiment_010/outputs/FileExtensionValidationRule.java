```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

/**
 * Validates the file extension of the input file.
 * This rule ensures that the application only accepts supported image formats:
 * PNG, JPG, and JPEG. If the file extension does not match this criteria,
 * the validation fails and returns a specific error message.
 */
public class FileExtensionValidationRule implements ValidationRule {

    /**
     * The error message to be returned if the file extension validation fails.
     * This constant holds the user-friendly text explaining the specific requirement.
     */
    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Validates whether the provided file name has an extension that is supported by the application.
     * It performs a case-insensitive check to see if the file name ends with .png, .jpg, or .jpeg.
     *
     * @param args An array of strings containing the file path/name. The method assumes
     *             the file name is the first element of the array (args[0]).
     * @return true if the file name matches the regex pattern for the supported extensions,
     *         false otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    /**
     * Retrieves the error message associated with this specific validation rule.
     * This message is typically displayed to the user if {@code isValid} returns false.
     *
     * @return The predefined error message string.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```