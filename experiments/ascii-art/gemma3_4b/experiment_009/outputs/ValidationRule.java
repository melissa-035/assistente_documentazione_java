```java
package pl.mgodyn.asciiart.application.validator;

import java.util.Arrays;

public interface ValidationRule {

    /**
     * Validates the input string array against the rule's criteria.
     *
     * @param args An array of strings to be validated. This is the input for the validation rule.
     * @return {@code true} if the input array is valid according to the rule, {@code false} otherwise.
     */
    boolean isValid(String[] args);

    /**
     * Returns an error message to be displayed if the input is invalid.
     *
     * @return A descriptive error message that explains why the input is invalid.
     */
    String getErrorMessage();
}
```