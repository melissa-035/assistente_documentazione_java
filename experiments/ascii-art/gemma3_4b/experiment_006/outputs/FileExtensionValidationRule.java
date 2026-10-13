```java
package pl.mgodyn.asciiart.application.validator.rules;

import java.util.Arrays;

public class FileExtensionValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "File not supported! It must be either .png, .jpg or .jpeg!";

    @Override
    public boolean isValid(String[] args) {
        if (args == null || args.length == 0) {
            return false;
        }
        String fileName = args[0];
        return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
    }

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```

```java
/**
 * Validates that the file extension is one of the supported types (.png, .jpg, or .jpeg).
 *
 * <p>
 * This validation rule checks if the file name ends with either ".png", ".jpg", or ".jpeg" (case-insensitive).
 * If the extension is not one of these, it returns an error message indicating that the file is not supported.
 * </p>
 *
 * @param args an array of strings representing the file name.
 * @return {@code true} if the file extension is valid (i.e., one of the supported types), {@code false} otherwise.
 * @throws IllegalArgumentException if the input array is null or has a length of zero.
 */
public boolean isValid(String[] args) {
    if (args == null || args.length == 0) {
        return false;
    }
    String fileName = args[0];
    return fileName.matches("(?i).*\\.(png|jpg|jpeg)$");
}
```

```java
/**
 * Returns the error message associated with this validation rule.
 *
 * <p>
 * The error message is used to provide feedback to the user if the file extension is not valid.
 * It clearly states that only .png, .jpg, or .jpeg files are supported.
 * </p>
 *
 * @return the error message.
 */
public String getErrorMessage() {
    return ERROR_MESSAGE;
}
```