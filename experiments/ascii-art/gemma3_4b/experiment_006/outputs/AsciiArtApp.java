```java
package pl.mgodyn.asciiart.application;

/**
 * The main application class for generating ASCII art from an image.
 * This class handles command-line arguments, validates them, and
 * then uses an ASCII art controller to generate the ASCII art.
 */
public class AsciiArtApp {

    /**
     * The main entry point of the application.
     *
     * @param args the command-line arguments.
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