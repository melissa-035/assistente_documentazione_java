```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

/**
 * The main entry point for the ASCII Art application.
 * This class orchestrates the conversion of an image file to ASCII art.
 * It handles command-line argument validation and delegates the processing
 * to the {@link AsciiArtController}.
 */
public class AsciiArtApp {

    /**
     * The main entry point of the application.
     * Validates the command-line arguments using the {@link AsciiArtValidationChain}.
     * If validation passes, it extracts the image path and initiates the
     * ASCII art generation process via the {@link AsciiArtController}.
     *
     * @param args The command-line arguments passed to the program. 
     *             The first argument is expected to be the path to the image file.
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