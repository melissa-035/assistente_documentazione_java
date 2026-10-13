```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 *  This class handles the conversion and printing of images to ASCII art.
 *  It utilizes the AsciiArtService for the conversion logic and ImageUtils
 *  for image reading. The DefaultScreenSizeProvider is used to determine the
 *  appropriate dimensions for the ASCII art output.
 */
class AsciiArtController {

    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Runs the ASCII art conversion and printing process for a given image.
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