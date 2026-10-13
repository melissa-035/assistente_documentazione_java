```java
/**
 * Implements {@link ValidationRule} to validate file extensions.
 * This rule checks if the provided file name has a supported image extension
 * (.png, .jpg, or .jpeg). The check is performed in a case-insensitive manner.
 */
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

public class FileExtensionValidationRule implements ValidationRule {

    /**
     * Error message displayed when the file extension is not one of the supported types.
     */
    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Validates the file extension of the input file name.
     * 
     * @param args The command line arguments array. The first element (args[0]) 
     *             is expected to be the file name to validate.
     * @return {@code true} if the file name matches the regular expression for 
     *         supported image formats (.png, .jpg, .jpeg), {@code false} otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    /**
     * Retrieves the error message associated with file extension validation failures.
     * 
     * @return The error message {@code String}.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```