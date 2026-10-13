```java
/**
 * The main application class serving as the entry point for the ASCII art generator.
 * It initializes the application, validates command-line arguments to ensure they are
 * correctly formatted, and orchestrates the process of converting an image file into
 * an ASCII art representation.
 */
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

/**
 * The application's entry point. It validates the provided command-line arguments
 * using the validation chain. If validation fails, the application terminates.
 * Upon successful validation, it extracts the image file path and delegates the
 * ASCII art generation to the controller.
 *
 * @param args the command-line arguments, where the first element is the path to the image file.
 */
public class AsciiArtApp {

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