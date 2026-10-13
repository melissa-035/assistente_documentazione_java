```java
package pl.mgodyn.asciiart.application.validator;

/**
 * Defines a contract for validating input arguments against a specific business rule.
 * <p>
 * This interface is part of the validation pipeline. Implementations are expected to
 * evaluate a set of arguments and determine their validity based on the rule's criteria.
 * If the rule fails, a descriptive error message can be retrieved via {@link #getErrorMessage()}.
 * </p>
 *
 * @author mgodyn
 */
public interface ValidationRule {

    /**
     * Validates the provided arguments against this specific rule.
     *
     * @param args the array of arguments to be evaluated
     * @return {@code true} if the arguments satisfy the validation rule, {@code false} otherwise
     */
    boolean isValid(String[] args);

    /**
     * Retrieves a user-friendly error message describing the reason for validation failure.
     *
     * @return the error message if validation fails; an empty string or {@code null}
     *         otherwise
     */
    String getErrorMessage();
}
```