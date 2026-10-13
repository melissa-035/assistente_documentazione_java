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
 * Test class for ValidationRule.
 *
 * This class contains JUnit tests for the ValidationRule class.
 * It tests the behavior of the FileExistenceValidationRule and
 * FileExtensionValidationRule.
 */
class ValidationRuleTest {

    /**
     * Tests that the LengthValidationRule returns false when provided with empty arguments.
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
     * Tests that the FileExistenceValidationRule returns true when provided with a path to an
     * existing file.
     *
     * @param filePath the path to the existing file.
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
     * Tests that the FileExistenceValidationRule returns false when provided with a path to a
     * non-existent file.
     *
     * @param filePath the path to the non-existent file.
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
     * Tests that the FileExtensionValidationRule returns true when provided with a file
     * having a proper extension.
     *
     * @param filePath the path to the file with a proper extension.
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
     * Tests that the FileExtensionValidationRule returns false when provided with a file
     * having a wrong extension.
     *
     * @param filePath the path to the file with a wrong extension.
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