```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

/**
 * Implements a validation rule to check if a file has a supported image extension.
 * <p>
 * This class validates that the provided file name ends with one of the supported
 * extensions: .png, .jpg, or .jpeg (case-insensitive).
 * </p>
 *
 * @see ValidationRule
 */
public class FileExtensionValidationRule implements ValidationRule {

    /**
     * The error message returned when a file extension is not supported.
     * <p>
     * This message explicitly lists the acceptable file formats for the user.
     * </p>
     */
    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Validates if the file name extracted from the arguments array has a supported image extension.
     * <p>
     * This method assumes the file name is located at index 0 of the arguments array.
     * It uses a case-insensitive regular expression to match the file extension against
     * the allowed types (.png, .jpg, .jpeg).
     * </p>
     *
     * @param args the array of command line arguments where the first element (index 0) is the file name.
     * @return {@code true} if the file name matches the extension pattern, {@code false} otherwise.
     * @throws ArrayIndexOutOfBoundsException if the {@code args} array is empty or null.
     */
    @Override
    public boolean isValid(String[] args) {
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    /**
     * Retrieves the error message associated with invalid file extensions.
     *
     * @return the error message string indicating the supported file formats.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```