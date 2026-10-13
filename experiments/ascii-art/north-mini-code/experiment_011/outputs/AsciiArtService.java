```java
/**
 * Service class for converting images to ASCII art and printing the result.
 * It uses a {@link Converter} to map pixels to ASCII characters and an
 * {@link AsciiArtPrinter} to accumulate and output the art.
 */
package pl.mgodyn.asciiart.service;

import pl.mgodyn.asciiart.converter.Converter;
import pl.mgodyn.asciiart.io.AsciiArtPrinter;

import java.awt.image.BufferedImage;

public class AsciiArtService {

    private final Converter converter;
    private final AsciiArtPrinter printer;

    public AsciiArtService() {
        this.converter = new Converter();
        this.printer = new AsciiArtPrinter();
    }

    /**
     * Converts the given BufferedImage to ASCII art.
     * For each pixel in the image, it converts the pixel value to an ASCII character
     * using the {@link Converter} and adds the character to the current row.
     * After processing all pixels of a row, a new row is started.
     *
     * @param image the image to convert
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
     * Prints the generated ASCII art to the standard output.
     */
    public void print() {
        printer.print();
    }
}
```