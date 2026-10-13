```java
package pl.mgodyn.asciiart.converter;

/**
 * Converts a pixel color to an ASCII art character based on its brightness.
 * <p>
 * This class uses a predefined set of ASCII characters that vary in visual density
 * to represent different levels of brightness. The mapping is done by converting the
 * pixel's RGB components to a grayscale brightness value (0-255) and then mapping that
 * to an index in the ASCII character set.
 * </p>
 *
 * @author (unknown)
 * @version 1.0
 */
public class Converter {

    /**
     * String of ASCII characters ordered from lightest to darkest, used to map brightness to a character.
     */
    private static final String ASCII_CHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

    /**
     * Maximum possible brightness value (255), since RGB components range from 0 to 255.
     */
    private static final short MAX_POSSIBLE_BRIGHTNESS = 255;

    /**
     * Proportion factor to map brightness (0-255) to an index in the {@link #ASCII_CHARS} array.
     * Calculated as {@code (ASCII_CHARS.length() - 1) / MAX_POSSIBLE_BRIGHTNESS}.
     */
    private static final double PROPORTION = (double) (ASCII_CHARS.length() - 1) / MAX_POSSIBLE_BRIGHTNESS;

    /**
     * Converts an integer pixel representation to an ASCII character representing its brightness.
     *
     * @param pixel the pixel color as an integer (commonly ARGB format). The red, green, and blue
     *             components are extracted to compute the average brightness.
     * @return a {@code char} from the {@link #ASCII_CHARS} set that corresponds to the pixel's brightness.
     */
    public char convertPixel2Ascii(int pixel) {
        var brightness = convertRGB2Brightness(pixel);
        return convertBrightness2Ascii(brightness);
    }

    /**
     * Calculates the average brightness (0-255) from the RGB components of a pixel.
     *
     * @param pixel the pixel color as an integer (commonly ARGB format).
     * @return the average of the red, green, and blue components.
     */
    private int convertRGB2Brightness(int pixel) {
        var red = getRedFromPixel(pixel);
        var blue = getBlueFromPixel(pixel);
        var green = getGreenFromPixel(pixel);
        return (red + green + blue) / 3;
    }

    /**
     * Extracts the red component from a pixel integer.
     *
     * @param pixel the pixel color as an integer (commonly ARGB format).
     * @return the red component value (0-255).
     */
    private int getRedFromPixel(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    /**
     * Extracts the green component from a pixel integer.
     *
     * @param pixel the pixel color as an integer (commonly ARGB format).
     * @return the green component value (0-255).
     */
    private int getGreenFromPixel(int pixel) {
        return pixel & 0xff;
    }

    /**
     * Extracts the blue component from a pixel integer.
     *
     * @param pixel the pixel color as an integer (commonly ARGB format).
     * @return the blue component value (0-255).
     */
    private int getBlueFromPixel(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    /**
     * Maps a brightness value (0-255) to the corresponding ASCII character.
     *
     * @param brightness the brightness value (0-255). Values outside this range are clamped implicitly
     *                   by the index calculation due to the proportion factor.
     * @return the ASCII character representing the given brightness.
     */
    private char convertBrightness2Ascii(int brightness) {
        int asciiChar = (int) Math.round(brightness * PROPORTION);
        return ASCII_CHARS.charAt(asciiChar);
    }
}
```