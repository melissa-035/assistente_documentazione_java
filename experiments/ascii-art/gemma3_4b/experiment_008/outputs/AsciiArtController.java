```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * The AsciiArtController class handles the conversion and printing of an image to ASCII art.
 * It uses the AsciiArtService to perform the conversion and printing operations, 
 * leveraging ImageUtils for image reading and a DefaultScreenSizeProvider for sizing.
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