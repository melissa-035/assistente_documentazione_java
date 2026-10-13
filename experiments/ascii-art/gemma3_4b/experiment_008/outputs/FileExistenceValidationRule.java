```java
package pl.mgodyn.asciiart.application.validator.rules;

import pl.mgodyn.asciiart.application.validator.ValidationRule;

import java.io.File;

/**
 * A validation rule that checks if a file exists.
 * <p>
 * This class implements the {@link ValidationRule} interface to validate that a specified file exists.
 * It takes the file path as an argument and returns {@code true} if the file exists, {@code false} otherwise.
 * </p>
 */
public class FileExistenceValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "File does not exist!";

    /**
     * Checks if the specified file exists.
     *
     * @param args An array of arguments, where the first element is expected to be the file path.
     * @return {@code true} if the file exists, {@code false} otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        File file = new File(args[0]);
        return file.exists();
    }

    /**
     * Returns the error message associated with the file existence validation rule.
     *
     * @return The error message "File does not exist!".
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```