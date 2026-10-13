```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.application.validator.AsciiArtValidationChain;

/**
 * This class serves as the main entry point for the ASCII Art application.
 * It validates command-line arguments and then delegates the processing to the {@link AsciiArtController}.
 */
public class AsciiArtApp {

    /**
     * The main method of the application.
     * It checks if the provided arguments are valid using {@link AsciiArtValidationChain}.
     * If validation fails, it returns; otherwise, it processes the image located at the given path.
     *
     * @param args Command-line arguments: the first argument is the path to the image file.
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