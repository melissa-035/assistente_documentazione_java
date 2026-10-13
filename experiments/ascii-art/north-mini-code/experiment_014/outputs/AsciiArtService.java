```java
package pl.mgodyn.asciiart.service;

import pl.mgodyn.asciiart.converter.Converter;
import pl.mgodyn.asciiart.io.AsciiArtPrinter;

import java.awt.image.BufferedImage;

/**
 * Service class responsible for converting a BufferedImage to ASCII art and printing it.
 * It uses a Converter to convert pixels to ASCII characters and an AsciiArtPrinter to output the result.
 */
public class AsciiArtService {

    private final Converter converter;
    private final AsciiArtPrinter printer;

    /**
     * Default constructor that initializes the Converter and AsciiArtPrinter instances.
     */
    public AsciiArtService() {
        this.converter = new Converter();
        this.printer = new AsciiArtPrinter();
    }

    /**
     * Converts each pixel of the given image to an ASCII character and builds rows.
     * Iterates over the image width and height, retrieves the RGB value of each pixel,
     * uses the converter to obtain the corresponding ASCII character, and adds it to the current row.
     * After processing all columns of a row, a new row is created.
     *
     * @param image the BufferedImage to be converted; must not be null
     * @throws NullPointerException if the image is null
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
     * Prints the accumulated ASCII art rows to the output using the AsciiArtPrinter.
     */
    public void print() {
        printer.print();
    }
}
```