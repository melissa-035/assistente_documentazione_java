```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * This class handles the conversion of an image to ASCII art and prints the result to the console.
 */
class AsciiArtController {

    /**
     * The AsciiArtService instance used to perform the conversion and printing.
     */
    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Runs the ASCII art conversion process.
     *
     * @param imagePath The path to the image file to convert.
     */
    void run(final String imagePath) {
        // Read the image using ImageUtils, providing a default screen size provider.
        BufferedImage image = ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider());

        // Convert the image to ASCII art using the AsciiArtService.
        asciiArtService.convert(image);

        // Print the ASCII art to the console using the AsciiArtService.
        asciiArtService.print();
    }
}
```