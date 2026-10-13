```java
package pl.mgodyn.application;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the AsciiArtValidationChain class.
 * This test class utilizes JUnit 5 parameterized tests to validate the
 * {@link AsciiArtValidationChain} class with various input arguments.
 * It covers scenarios for valid and invalid ASCII art file extensions.
 */
class AsciiArtValidationChainTest {

    /**
     * Tests the {@link AsciiArtValidationChain.validateAndHandleErrors} method with valid arguments,
     * asserting that the method returns true, indicating a valid scenario.
     */
    @ParameterizedTest
    @MethodSource("validArgs")
    void givenValidArguments_whenValidating_thenIsValid(String[] args) {
        assertTrue(AsciiArtValidationChain.validateAndHandleErrors(args));
    }

    /**
     * Tests the {@link AsciiArtValidationChain.validateAndHandleErrors} method with invalid arguments,
     * asserting that the method returns false, indicating an invalid scenario.
     */
    @ParameterizedTest
    @MethodSource("invalidArgs")
    void givenInvalidArguments_whenValidating_thenIsInvalid(String[] args) {
        assertFalse(AsciiArtValidationChain.validateAndHandleErrors(args));
    }

    /**
     * Generates a stream of arguments for the {@link AsciiArtValidationChainTest#givenValidArguments_whenValidating_thenIsValid}
     * and {@link AsciiArtValidationChainTest#givenInvalidArguments_whenValidating_thenIsInvalid} parameterized tests.
     * The arguments are designed to test the validation logic with different file extensions.
     *
     * @return A stream of Arguments, each representing a set of arguments for the validation test.
     * @throws IOException If an error occurs during file creation.
     */
    private static Stream<Arguments> validArgs() throws IOException {
        File tempJpgFile = Files.createTempFile("test", ".jpg").toFile();
        File tempJpegFile = Files.createTempFile("test", ".jpeg").toFile();
        File tempPngFile = Files.createTempFile("test", ".png").toFile();

        String[] argsWithJpg = {tempJpgFile.getPath()};
        String[] argsWithJpeg = {tempJpegFile.getPath()};
        String[] argsWithPng = {tempPngFile.getPath()};

        return Stream.of(
                Arguments.of((Object) argsWithJpg),
                Arguments.of((Object) argsWithJpeg),
                Arguments.of((Object) argsWithPng)
        );
    }

    /**
     * Generates a stream of arguments for the {@link AsciiArtValidationChainTest#givenInvalidArguments_whenValidating_thenIsInvalid}
     * parameterized test. These arguments are used to test scenarios with invalid file extensions.
     *
     * @return A stream of Arguments, each representing a set of arguments for the validation test.
     * @throws IOException If an error occurs during file creation.
     */
    private static Stream<Arguments> invalidArgs() throws IOException {
        File tempTxtFile = Files.createTempFile("test", ".txt").toFile();
        String tempTxtFilePath = tempTxtFile.getAbsolutePath();

        String[] emptyArgs = {};
        String[] argsWithTxt = {tempTxtFilePath};
        String[] argsWith2Txt = {tempTxtFilePath, tempTxtFilePath};

        return Stream.of(
                Arguments.of((Object) emptyArgs),
                Arguments.of((Object) argsWithTxt),
                Arguments.of((Object) argsWith2Txt)
        );
    }
}
```