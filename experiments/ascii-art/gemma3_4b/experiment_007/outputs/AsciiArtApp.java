```java
package pl.mgodyn.asciiart.application;

/**
 * The main application class for generating ASCII art from an image.
 * This class orchestrates the process of validating command-line arguments,
 * running the ASCII art generation controller, and handling potential errors.
 */
public class AsciiArtApp {

    /**
     * The main entry point of the application.
     *
     * @param args the command-line arguments, where the first argument should
     *            be the path to the image file.
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