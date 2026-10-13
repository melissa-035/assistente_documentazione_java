```java
/**
 * Defines a contract for validating input strings in an ASCII art application.
 * Each implementation represents a specific validation rule and should
 * check if the provided arguments are valid according to its logic.
 *
 * @author mgodyn
 */
public interface ValidationRule {

    /**
     * Checks if the provided arguments are valid according to this rule's logic.
     *
     * @param args the input arguments to validate.
     * @return {@code true} if the arguments are valid, {@code false} otherwise.
     */
    boolean isValid(String[] args);

    /**
     * Returns a user-friendly error message to be displayed if the validation
     * fails.
     *
     * @return the error message.
     */
    String getErrorMessage();
}
```