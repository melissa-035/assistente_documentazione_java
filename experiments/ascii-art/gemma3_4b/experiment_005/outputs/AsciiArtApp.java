```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

/**
 * The main application class for generating ASCII art from an image.
 * This class handles command-line arguments, validates them, and orchestrates
 * the ASCII art generation process using the AsciiArtController.
 */
public class AsciiArtApp {

    /**
     * The main method of the application.
     *
     * @param args The command-line arguments.  The first argument is expected to be the path to the image file.
     */
    public static void main(String[] args) {
        // Validate the command-line arguments using the AsciiArtValidationChain.
        if (!AsciiArtValidationChain.validateAndHandleErrors(args)) {
            // If validation fails, the program exits.
            return;
        }

        // Get the image path from the command-line arguments.
        final String imagePath = args[0];

        // Create an instance of the AsciiArtController.
        AsciiArtController asciiArtController = new AsciiArtController();

        // Run the ASCII art generation process using the controller.
        asciiArtController.run(imagePath);
    }
}
```