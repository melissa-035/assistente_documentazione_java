```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

/**
 * The main application class for generating ASCII art from an image file.
 * This class orchestrates the process of validating input arguments, 
 * running the ASCII art generation logic, and handling potential errors.
 */
public class AsciiArtApp {

    /**
     * The main method of the application.
     *
     * This method takes command-line arguments, validates them using the
     * AsciiArtValidationChain, and then initiates the ASCII art generation
     * process using the AsciiArtController.
     *
     * @param args The command-line arguments. The first argument is expected
     *             to be the path to the image file.
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