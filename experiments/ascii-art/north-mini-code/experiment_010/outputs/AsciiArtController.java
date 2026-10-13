```java
package pl.mgodyn.asciiart.application;

import pl.mgodyn.asciiart.service.AsciiArtService;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * Controller class responsible for orchestrating the conversion of a real-world image
 * into ASCII art.
 * <p>
 * It utilizes {@link AsciiArtService} to perform the core conversion and printing logic,
 * and relies on {@link ImageUtils} to handle the low-level reading of image files
 * with a default screen size provider.
 * </p>
 */
class AsciiArtController {

    /**
     * The service component that handles the conversion of a {@link BufferedImage}
     * into ASCII characters and manages the printing of the resulting art.
     */
    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Executes the primary application workflow.
     * <p>
     * The method performs the following sequential steps:
     * <ol>
     *   <li>Reads the image file from the specified path using {@link ImageUtils}.</li>
     *   <li>Passes the loaded {@link BufferedImage} to {@link AsciiArtService} for conversion.</li>
     *   <li>Invokes the printing mechanism via the same service to display the output.</li>
     * </ol>
     * </p>
     *
     * @param imagePath The file path to the image source to be converted.
     */
    void run(final String imagePath) {
        BufferedImage image = ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider());
        asciiArtService.convert(image);
        asciiArtService.print();
    }
}
```