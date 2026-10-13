```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

public class FileExtensionValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Validates if the file extension is one of the allowed extensions (.png, .jpg, or .jpeg).
     *
     * @param args An array of arguments, expected to contain the filename.
     * @return True if the filename has a valid extension, false otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
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