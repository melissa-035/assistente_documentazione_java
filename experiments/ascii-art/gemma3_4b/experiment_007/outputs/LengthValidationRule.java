```java
/**
 * A validation rule that checks if the input array has exactly one element.
 * This rule is commonly used to ensure that a validation rule receives the
 * expected number of arguments.
 *
 * <p>
 * This class implements the {@link ValidationRule} interface and provides a
 * method to validate the input array.
 * </p>
 *
 * @author mgodyn
 * @version 1.0
 */
public class LengthValidationRule implements ValidationRule {

    private final String ERROR_MESSAGE = "Too many or no arguments!";

    /**
     * Checks if the input array has exactly one element.
     *
     * @param args the input array to validate.
     * @return {@code true} if the array has exactly one element, {@code false}
     *         otherwise.
     */
    @Override
    public boolean isValid(String[] args) {
        return args.length == 1;
    }

    /**
     * Returns the error message associated with this validation rule.
     *
     * @return the error message.
     */
    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
```