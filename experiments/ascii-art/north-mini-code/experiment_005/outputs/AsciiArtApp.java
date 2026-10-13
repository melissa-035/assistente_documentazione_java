```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

/**
 * The main application class for the ASCII art conversion tool.
 * This class serves as the entry point for the program, validating
 * command-line arguments and delegating the processing to the
 * {@link AsciiArtController}.
 *
 * @author mgodyn
 * @version 1.0
 */
public class AsciiArtApp {

    /**
     * The application entry point.
     * Validates the provided arguments and runs the ASCII art conversion
     * for the specified image file.
     *
     * @param args the command-line arguments; the first argument is the path
     *             to the image file to convert.
     * @see AsciiArtValidationChain
     */
    public static void main(String[] args) {
        if (!AsciiArtValidationChain.validateAndHandleErrors(args)) {
            return;
        }

        final String imagePath = args[0];

        AsciiArtController asciiArtController = new AsciiArtController();
        asciiArtController.run(imagePath);
    }
}
```