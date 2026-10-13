```java
package pl.mgodyn.asciiart.application.validator;

import java.util.Arrays;

/**
 * An interface defining a rule for validating ASCII art data.
 * Each validation rule is responsible for checking a specific aspect
 * of the data and returning a boolean indicating validity and an
 * error message if the data is invalid.
 */
public interface ValidationRule {

    /**
     * Checks if the provided arguments (typically command-line arguments)
     * satisfy the rule's criteria.
     *
     * @param args The arguments to validate.  This can be any type of array,
     *             typically a String array.
     * @return True if the arguments are valid according to this rule,
     *         false otherwise.
     */
    boolean isValid(String[] args);

    /**
     * Returns an error message to display if the validation rule fails.
     * This is used to provide informative feedback to the user about
     * why the data is invalid.
     *
     * @return The error message.
     */
    String getErrorMessage();
}
```