```java
/**
 * Validates if a file exists.
 *
 * This class implements the {@link ValidationRule} interface to check if a
 * specified file exists. If the file exists, the validation is considered
 * to be valid. Otherwise, it is invalid.
 */
public class FileExistenceValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "File does not exist!";

    @Override
    public boolean isValid(String[] args) {
        File file = new File(args[0]);
        return file.exists();
    }

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```