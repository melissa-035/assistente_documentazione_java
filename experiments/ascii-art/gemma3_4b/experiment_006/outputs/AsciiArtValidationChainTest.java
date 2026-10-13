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
 * Tests the AsciiArtValidationChain class. This class contains parameterized tests
 * to verify the validation logic for ASCII art files. The tests cover both
 * valid and invalid file paths, ensuring that the chain correctly identifies
 * and handles errors in the input files.
 */
class AsciiArtValidationChainTest {

    /**
     * Tests the validation logic with valid arguments.
     *
     * @param args The arguments to be passed to the AsciiArtValidationChain.
     * @throws IOException if an I/O error occurs during file operations.
     */
    @ParameterizedTest
    @MethodSource("validArgs")
    void givenValidArguments_whenValidating_thenIsValid(String[] args) throws IOException {
        assertTrue(AsciiArtValidationChain.validateAndHandleErrors(args));
    }

    /**
     * Tests the validation logic with invalid arguments.
     *
     * @param args The arguments to be passed to the AsciiArtValidationChain.
     * @throws IOException if an I/O error occurs during file operations.
     */
    @ParameterizedTest
    @MethodSource("invalidArgs")
    void givenInvalidArguments_whenValidating_thenIsInvalid(String[] args) throws IOException {
        assertFalse(AsciiArtValidationChain.validateAndHandleErrors(args));
    }

    /**
     * Generates a stream of arguments for the valid arguments test.
     *
     * @return A stream of Arguments objects, each representing a set of valid arguments.
     * @throws IOException if an I/O error occurs during file operations.
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
     * Generates a stream of arguments for the invalid arguments test.
     *
     * @return A stream of Arguments objects, each representing a set of invalid arguments.
     * @throws IOException if an I/O error occurs during file operations.
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