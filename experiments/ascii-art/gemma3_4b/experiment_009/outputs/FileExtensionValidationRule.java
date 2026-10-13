```java
package pl.mgodyn.asciiart.application.validator.rules;

import java.util.Arrays;

/**
 * A validation rule to check if a file extension is one of the supported formats (.png, .jpg, or .jpeg).
 * <p>
 * This class implements the {@link ValidationRule} interface to validate file extensions.
 * It performs a regular expression match to ensure the file name ends with either "png", "jpg", or "jpeg"
 * (case-insensitive).
 */
public class FileExtensionValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Checks if the given file name has a supported file extension.
     *
     * @param args An array of strings, where the first element is the file name to validate.
     * @return {@code true} if the file name has a supported extension (e.g., .png, .jpg, or .jpeg),
     *         {@code false} otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        if (args == null || args.length == 0) {
            return false; // Or throw an exception, depending on desired behavior
        }
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    /**
     * Returns the error message to be displayed if the file extension is invalid.
     *
     * @return The error message indicating that the file is not supported.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```