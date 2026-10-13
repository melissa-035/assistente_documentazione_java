```java
package pl.mgodyn.asciiart.converter;

/**
 * Converts an RGB pixel value to an ASCII character representing its brightness.
 *
 * <p>The class uses a predefined set of ASCII characters ordered from darkest to lightest.
 * The brightness of a pixel is calculated as the average of its red, green, and blue components.
 * The brightness is then mapped to an index in the ASCII character array, which is used to retrieve
 * the corresponding character.
 * </p>
 */
public class Converter {

    /**
     * The set of ASCII characters used to represent different brightness levels, ordered from darkest to lightest.
     */
    private static final String ASCII_CHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

    /**
     * The maximum possible brightness value for a pixel (full intensity).
     */
    private static final short MAX_POSSIBLE_BRIGHTNESS = 255;

    /**
     * The proportion to map brightness (0-255) to an index in the ASCII character array.
     */
    private static final double PROPORTION = (double) (ASCII_CHARS.length() - 1) / MAX_POSSIBLE_BRIGHTNESS;

    /**
     * Converts a pixel's RGB value to an ASCII character representing its brightness.
     *
     * @param pixel the RGB pixel value (packed in an integer: 0xRRGGBB)
     * @return the ASCII character that corresponds to the pixel's brightness
     */
    public char convertPixel2Ascii(int pixel) {
        var brightness = convertRGB2Brightness(pixel);
        return convertBrightness2Ascii(brightness);
    }

    /**
     * Calculates the average brightness of an RGB pixel.
     *
     * @param pixel the RGB pixel value
     * @return the brightness value (0-255)
     */
    private int convertRGB2Brightness(int pixel) {
        var red = getRedFromPixel(pixel);
        var blue = getBlueFromPixel(pixel);
        var green = getGreenFromPixel(pixel);
        return (red + green + blue) / 3;
    }

    /**
     * Extracts the red component from an RGB pixel value.
     *
     * @param pixel the RGB pixel value
     * @return the red component (0-255)
     */
    private int getRedFromPixel(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    /**
     * Extracts the blue component from an RGB pixel value.
     *
     * @param pixel the RGB pixel value
     * @return the blue component (0-255)
     */
    private int getBlueFromPixel(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    /**
     * Extracts the green component from an RGB pixel value.
     *
     * @param pixel the RGB pixel value
     * @return the green component (0-255)
     */
    private int getGreenFromPixel(int pixel) {
        return pixel & 0xff;
    }

    /**
     * Maps a brightness value to the corresponding ASCII character.
     *
     * @param brightness the brightness value (0-255)
     * @return the ASCII character that represents the given brightness
     */
    private char convertBrightness2Ascii(int brightness) {
        int asciiChar = (int) Math.round(brightness * PROPORTION);
        return ASCII_CHARS.charAt(asciiChar);
    }
}
```