```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

public class FileExtensionValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    /**
     * Validates if the provided file name has the correct extension.
     * This rule checks if the file name ends with either ".png", ".jpg", or ".jpeg" (case-insensitive).
     *
     * @param args an array of strings, where the first element is expected to be the file name.
     * @return true if the file name has a valid extension, false otherwise.
     * @see ValidationRule
     */
    @Override
    public boolean isValid(String[] args) {
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    /**
     * Returns the error message to be displayed when the validation fails.
     *
     * @return the error message.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```