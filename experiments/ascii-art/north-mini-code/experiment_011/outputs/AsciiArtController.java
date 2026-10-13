```java
/**
 * Controller class that manages the application flow for generating ASCII art.
 * It orchestrates the reading of an image file, the conversion process to ASCII art,
 * and the printing of the resulting output to the standard output.
 */
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * Controller class that manages the application flow for generating ASCII art.
 * It orchestrates the reading of an image file, the conversion process to ASCII art,
 * and the printing of the resulting output to the standard output.
 */
class AsciiArtController {

    /**
     * The service component responsible for converting BufferedImage objects into
     * ASCII art strings and printing them to the console.
     */
    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Executes the main application workflow. Reads an image from the specified file path,
     * converts it into ASCII art, and prints the result.
     *
     * @param imagePath the file path to the image to be processed.
     * @see AsciiArtService
     */
    void run(final String imagePath) {
        BufferedImage image = ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider());
        asciiArtService.convert(image);
        asciiArtService.print();
    }
}
```