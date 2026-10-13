```java
/**
 * Defines a rule for validating input strings.
 *
 * This interface represents a single validation rule that can be applied to
 * a collection of strings. Each rule performs a specific validation check
 * and returns an error message if the input fails to meet the rule's criteria.
 */
public interface ValidationRule {

    /**
     * Validates the given string array against this rule.
     *
     * @param args the string array to validate.
     * @return {@code true} if the input strings are valid according to this
     *         rule, {@code false} otherwise.
     */
    boolean isValid(String[] args);

    /**
     * Returns an error message to be displayed if the input strings fail to
     * meet the validation criteria.
     *
     * @return the error message.
     */
    String getErrorMessage();
}
```