```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * The {@code AsciiArtController} class serves as the main entry point and coordinator
 * for the ASCII art generation application. It is responsible for initiating the
 * workflow that reads an image file from the file system, converts it into an ASCII
 * representation, and outputs the resulting art to the console.
 */
class AsciiArtController {

    /**
     * The service instance responsible for handling the conversion of a {@link BufferedImage}
     * into ASCII characters and managing the printing of the generated output.
     * This field is marked {@code final}, indicating that it is initialized once during
     * object construction and cannot be reassigned.
     */
    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Initiates the application pipeline by reading an image file, converting it to ASCII,
     * and printing the result.
     *
     * @param imagePath The file system path (absolute or relative) to the image file
     *                  that needs to be processed. The path is passed to the image utility
     *                  for loading.
     * @throws IOException If an I/O error occurs during the reading of the image file,
     *                     such as the file not being found or being corrupted.
     */
    void run(final String imagePath) {
        BufferedImage image = ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider());
        asciiArtService.convert(image);
        asciiArtService.print();
    }
}
```