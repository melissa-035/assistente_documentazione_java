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
 * Test class for the AsciiArtValidationChain class.
 * This class contains parameterized tests to verify the
 * functionality of the AsciiArtValidationChain, specifically
 * checking if it correctly validates image files based on provided arguments.
 */
class AsciiArtValidationChainTest {

    /**
     * Tests the AsciiArtValidationChain with valid arguments, asserting that
     * the validation process returns true. Uses a temporary file to generate
     * a valid image file.
     *
     * @param args an array of strings representing the arguments to be passed
     *             to the AsciiArtValidationChain.
     */
    @ParameterizedTest
    @MethodSource("validArgs")
    void givenValidArguments_whenValidating_thenIsValid(String[] args) {
        assertTrue(AsciiArtValidationChain.validateAndHandleErrors(args));
    }

    /**
     * Tests the AsciiArtValidationChain with invalid arguments, asserting that
     * the validation process returns false.  Uses temporary files to test
     * scenarios with non-image files.
     *
     * @param args an array of strings representing the arguments to be passed
     *             to the AsciiArtValidationChain.
     */
    @ParameterizedTest
    @MethodSource("invalidArgs")
    void givenInvalidArguments_whenValidating_thenIsInvalid(String[] args) {
        assertFalse(AsciiArtValidationChain.validateAndHandleErrors(args));
    }

    /**
     * Generates a stream of Arguments for the "validArgs" method.  This stream
     * provides arguments for the {@link AsciiArtValidationChainTest#givenValidArguments_whenValidating_thenIsValid}
     * test method.
     *
     * @return A stream of Arguments for testing.
     * @throws IOException if an error occurs during file creation.
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
     * Generates a stream of Arguments for the "invalidArgs" method.  This stream
     * provides arguments for the {@link AsciiArtValidationChainTest#givenInvalidArguments_whenValidating_thenIsInvalid}
     * test method.
     *
     * @return A stream of Arguments for testing.
     * @throws IOException if an error occurs during file creation.
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