```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * A controller class responsible for converting an image to ASCII art and printing it to the console.
 * This class encapsulates the core logic for processing an image and generating the ASCII representation.
 */
public class AsciiArtController {

    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Runs the ASCII art conversion process.
     * <p>
     * Reads an image from the specified path, converts it to ASCII art using the {@link AsciiArtService},
     * and prints the resulting ASCII art to the console.
     * </p>
     *
     * @param imagePath The path to the image file to convert.
     */
    void run(final String imagePath) {
        BufferedImage image = ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider());
        asciiArtService.convert(image);
        asciiArtService.print();
    }
}
```