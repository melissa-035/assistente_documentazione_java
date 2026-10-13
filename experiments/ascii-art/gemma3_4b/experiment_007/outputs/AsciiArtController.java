```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * The AsciiArtController class handles the conversion and printing of images
 * to ASCII art. It orchestrates the reading of the image, the conversion
 * process, and the final printing of the ASCII art.
 */
class AsciiArtController {

    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Runs the ASCII art conversion and printing process for a given image.
     *
     * @param imagePath The path to the image file.
     */
    void run(final String imagePath) {
        BufferedImage image = ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider());
        asciiArtService.convert(image);
        asciiArtService.print();
    }
}
```