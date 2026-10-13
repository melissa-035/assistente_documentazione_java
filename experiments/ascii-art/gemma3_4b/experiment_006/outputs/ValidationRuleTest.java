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
 * This class contains tests for the various validation rules.
 */
class ValidationRuleTest {

    /**
     * Tests the LengthValidationRule with an empty arguments array.
     * It asserts that the validation rule returns false, indicating that the
     * length is invalid.
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
     * Tests the FileExistenceValidationRule with a path to an existing file.
     * It asserts that the validation rule returns true, indicating that the
     * file exists.
     *
     * @param path the path to the file.
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
     * Tests the FileExistenceValidationRule with a path to a non-existent file.
     * It asserts that the validation rule returns false, indicating that the
     * file does not exist.
     *
     * @param path the path to the file.
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
     * Tests the FileExtensionValidationRule with a proper file extension.
     * It asserts that the validation rule returns true, indicating that the
     * file extension is valid.
     *
     * @param args the arguments passed to the validation rule.
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
     * Tests the FileExtensionValidationRule with a wrong file extension.
     * It asserts that the validation rule returns false, indicating that the
     * file extension is invalid.
     *
     * @param args the arguments passed to the validation rule.
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