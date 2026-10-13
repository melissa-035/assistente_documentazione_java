```java
/**
 * Controller class responsible for orchestrating the ASCII art generation process.
 * This class serves as the main entry point for the application, managing the
 * workflow of loading an image file from a given path and converting it into
 * ASCII art using the {@link AsciiArtService}.
 *
 * @author mgodyn
 * @version 1.0
 */
class AsciiArtController {

    /**
     * The service component responsible for the core logic of converting a {@link BufferedImage}
     * into ASCII art characters and handling the output printing. This dependency is
     * injected via field initialization and is marked final for immutability.
     */
    private final AsciiArtService asciiArtService = new AsciiArtService();

    /**
     * Executes the primary application workflow.
     * <p>
     * This method reads an image from the specified file path, passes it to the
     * {@link AsciiArtService} for conversion to ASCII, and triggers the printing
     * of the resulting art to the console.
     *
     * @param imagePath The file path (relative or absolute) to the source image.
     *                  Supported formats depend on {@link ImageUtils}.
     * @throws IOException If an I/O error occurs while reading the image file
     *                     (e.g., file not found, unsupported image format).
     * @see ImageUtils
     * @see AsciiArtService
     */
    void run(final String imagePath) {
        BufferedImage image = ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider());
        asciiArtService.convert(image);
        asciiArtService.print();
    }
}
```