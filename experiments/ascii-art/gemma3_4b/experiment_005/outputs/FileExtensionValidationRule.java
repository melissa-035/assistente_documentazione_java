```java
package pl.mgodyn.asciiart.application.validator.rules;

import java.util.regex.Matcher;

/**
 * This class implements a validation rule to check if a file extension is one of the supported types: .png, .jpg, or .jpeg.
 * It extends the base ValidationRule interface.
 */
public class FileExtensionValidationRule implements ValidationRule {

    /**
     * The error message to be returned if the file extension is not supported.
     * This is a constant String that is used to provide a user-friendly error message.
     */
    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Checks if the provided file name has a valid extension (png, jpg, or jpeg).
     *
     * @param args An array of strings. The file name should be the first element of the array.
     * @return True if the file name has a valid extension, False otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        if (args == null || args.length == 0) {
            return false; // Handle null or empty input array
        }
        String fileName = args[0];
        // Use regex to match the file extension, case-insensitively
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    /**
     * Returns the error message to be displayed if the file extension is invalid.
     *
     * @return The error message.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```