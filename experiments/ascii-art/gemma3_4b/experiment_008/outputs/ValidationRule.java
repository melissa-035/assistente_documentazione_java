```java
package pl.mgodyn.asciiart.application.validator;

/**
 * Defines a rule for validating input data in an ASCII art application.
 * 
 * This interface represents a single validation rule that can be applied to
 * a set of arguments.  Each rule has an `isValid` method to check if the
 * arguments meet its criteria, and a `getErrorMessage` method to return an
 * error message if the rule fails.
 */
public interface ValidationRule {

    /**
     * Checks if the given arguments are valid according to this rule.
     * 
     * @param args The input arguments to validate.
     * @return True if the arguments are valid, false otherwise.
     */
    boolean isValid(String[] args);

    /**
     * Returns an error message to be displayed if the validation rule fails.
     * 
     * @return The error message.
     */
    String getErrorMessage();
}
```