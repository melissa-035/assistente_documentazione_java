```java
package pl.mgodyn.asciiart.service;

import pl.mgodyn.asciiart.converter.Converter;
import pl.mgodyn.asciiart.io.AsciiArtPrinter;

import java.awt.image.BufferedImage;

/**
 * Service class responsible for orchestrating the conversion of a {@link BufferedImage}
 * into ASCII art. This class acts as a facade, coordinating the conversion logic
 * performed by the {@link Converter} and the output operations handled by the {@link AsciiArtPrinter}.
 *
 * <p>The workflow is divided into two distinct phases:
 * <ol>
 *   <li>
 *     <b>Processing:</b> The {@link #convert(BufferedImage)} method iterates through
 *     the image pixels, converting each RGB value to the corresponding ASCII character
 *     and accumulating them row by row.
 *   </li>
 *   <li>
 *     <b>Rendering:</b> Once the image is fully processed, the {@link #print()} method
 *     sends the accumulated ASCII characters to the output destination (typically console).
 *   </li>
 * </ol>
 * </p>
 *
 * @author mgodyn
 */
public class AsciiArtService {

    /**
     * The converter instance responsible for mapping pixel color values (integers)
     * to their corresponding ASCII character representations.
     */
    private final Converter converter;

    /**
     * The printer instance responsible for managing the output stream of ASCII characters.
     * It handles adding characters to rows and creating new lines when the current row
     * is complete.
     */
    private final AsciiArtPrinter printer;

    /**
     * Constructs an {@code AsciiArtService} with default dependencies.
     *
     * <p>This constructor initializes the internal {@link Converter} and {@link AsciiArtPrinter}
     * using their default no-argument constructors.
     */
    public AsciiArtService() {
        this.converter = new Converter();
        this.printer = new AsciiArtPrinter();
    }

    /**
     * Converts a {@link BufferedImage} into ASCII art and populates the printer's buffer.
     *
     * <p>This method processes the image in row-major order (top-to-bottom, left-to-right).
     * For each pixel at coordinates (x, y), it retrieves the RGB value, passes it to the
     * {@link Converter}, and adds the resulting ASCII character to the current row.
     * After processing all columns in a specific row, a new row is created in the printer
     * to prepare for the next line of output.
     *
     * @param image the {@link BufferedImage} to be converted. Must not be null.
     */
    public void convert(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                var pixel = image.getRGB(x, y);
                var ascii = converter.convertPixel2Ascii(pixel);
                printer.add2Row(ascii);
            }
            printer.createNewRow();
        }
    }

    /**
     * Prints the generated ASCII art to the configured output destination.
     *
     * <p>This method triggers the {@link AsciiArtPrinter} to output the characters
     * that have been accumulated during the conversion process to the standard
     * output stream (e.g., the console).
     */
    public void print() {
        printer.print();
    }
}
```