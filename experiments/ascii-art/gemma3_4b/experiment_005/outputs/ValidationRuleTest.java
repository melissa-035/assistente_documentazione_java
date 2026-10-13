```java
package pl.mgodyn.application;

import org.junit.jupiter.api.Test;
import pl.mgodyn.asciiart.application.validator.rules.FileExistenceValidationRule;
import pl.mgodyn.asciiart.application.validator.rules.FileExtensionValidationRule;
import pl.mgodyn.asciiart.application.validator.rules.LengthValidationRule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the ValidationRule interface and its implementations.
 * This class contains unit tests to verify the behavior of the validation rules.
 */
class ValidationRuleTest {

    /**
     * Test case for the LengthValidationRule.
     * Verifies that an empty array of arguments results in an invalid validation.
     */
    @Test
    void givenEmptyArgs_whenValidatingLength_isInvalid() {
        // given
        String[] args = {};
        var lengthValidationRule = new LengthValidationRule();

        // when
        BooleanSupplier validateLength = () -> lengthValidationRule.isValid(args);

        // then
        assertFalse(validateLength);
    }

    /**
     * Test case for the FileExistenceValidationRule.
     * Verifies that a valid file path results in a valid validation.
     *
     * @param filePath The path to a temporary file.
     */
    @Test
    void givenPathToExistentFile_whenValidatingExistence_isValid() throws IOException {
        // given
        File tempFile = Files.createTempFile("test", ".jpg").toFile();
        assertTrue(tempFile.exists());
        String[] args = {tempFile.getPath()};
        var fileExistenceValidationRule = new FileExistenceValidationRule();

        // when
        BooleanSupplier validateFileExistence = () -> fileExistenceValidationRule.isValid(args);

        // then
        assertTrue(validateFileExistence);
    }

    /**
     * Test case for the FileExistenceValidationRule.
     * Verifies that a non-existent file path results in an invalid validation.
     *
     * @param filePath The path to a non-existent file.
     */
    @Test
    void givenPathToNonExistentFile_whenValidatingExistence_isInvalid() {
        // given
        String[] args = {"non_existing_file.jpg"};
        var fileExistenceValidationRule = new FileExistenceValidationRule();

        // when
        BooleanSupplier validateFileExistence = () -> fileExistenceValidationRule.isValid(args);

        // then
        assertFalse(validateFileExistence);
    }

    /**
     * Test case for the FileExtensionValidationRule.
     * Verifies that a file with a proper extension results in a valid validation.
     *
     * @param args An array of arguments containing the file path.
     */
    @Test
    void givenProperFileExtension_whenValidatingFileExtension_isValid() {
        // given
        String[] args = {"image.png"};
        var fileExtensionValidationRule = new FileExtensionValidationRule();

        // when
        BooleanSupplier validateFileExtension = () -> fileExtensionValidationRule.isValid(args);

        // then
        assertTrue(validateFileExtension);
    }

    /**
     * Test case for the FileExtensionValidationRule.
     * Verifies that a file with a wrong extension results in an invalid validation.
     *
     * @param args An array of arguments containing the file path.
     */
    @Test
    void givenWrongFileExtension_whenValidatingFileExtension_isInvalid() {
        // given
        String[] args = {"document.docx"};
        var fileExtensionValidationRule = new FileExtensionValidationRule();

        //when
        BooleanSupplier validateFileExtension = () -> fileExtensionValidationRule.isValid(args);

        // then
        assertFalse(validateFileExtension);
    }
}
```