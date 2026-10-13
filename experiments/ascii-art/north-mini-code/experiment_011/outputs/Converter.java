```java
/**
 * Converts an integer representing an RGB pixel value to an ASCII character
 * representing its brightness.
 *
 * <p>This class provides utility methods to perform the conversion of standard
 * 24-bit RGB color values (represented as a single integer) into ASCII art
 * characters. It utilizes a predefined string of ASCII characters ordered
 * from dark to light (e.g., {@code `^",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$})
 * to visually represent the intensity of the pixel.</p>
 *
 * <p>The conversion process involves two main steps:
 * <ol>
 *   <li>Decomposing the pixel integer into its Red, Green, and Blue components
 *       using bitwise operations.</li>
 *   <li>Calculating the average brightness and mapping this value to the
 *       corresponding index in the {@code ASCII_CHARS} string.</li>
 * </ol>
 * </p>
 *
 * @author pl.mgodyn
 * @see #convertPixel2Ascii(int)
 */
package pl.mgodyn.asciiart.converter;

public class Converter {

    private static final String ASCII_CHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
    private static final short MAX_POSSIBLE_BRIGHTNESS = 255;
    private static final double PROPORTION = (double) (ASCII_CHARS.length() - 1) / MAX_POSSIBLE_BRIGHTNESS;

    /**
     * Converts the specified RGB pixel value to the corresponding ASCII character
     * based on its brightness.
     *
     * @param pixel the integer representing the RGB color (0xRRGGBB format).
     * @return the ASCII character that corresponds to the pixel's brightness.
     */
    public char convertPixel2Ascii(int pixel) {
        var brightness = convertRGB2Brightness(pixel);
        return convertBrightness2Ascii(brightness);
    }

    private int convertRGB2Brightness(int pixel) {
        var red = getRedFromPixel(pixel);
        var blue = getBlueFromPixel(pixel);
        var green = getGreenFromPixel(pixel);
        return (red + green + blue) / 3;
    }

    private int getRedFromPixel(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    private int getBlueFromPixel(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    private int getGreenFromPixel(int pixel) {
        return pixel & 0xff;
    }

    private char convertBrightness2Ascii(int brightness) {
        int asciiChar = (int) Math.round(brightness * PROPORTION);
        return ASCII_CHARS.charAt(asciiChar);
    }
}
```