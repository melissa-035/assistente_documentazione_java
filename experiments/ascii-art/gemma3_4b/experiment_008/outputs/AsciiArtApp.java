```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

/**
 * The main application class for generating ASCII art from an image.
 * This class handles command-line arguments, validates them, and then
 * executes the ASCII art generation process using the AsciiArtController.
 */
public class AsciiArtApp {

    /**
     * The main entry point of the application.
     *
     * @param args Command-line arguments. The first argument is expected to be the path to the image file.
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