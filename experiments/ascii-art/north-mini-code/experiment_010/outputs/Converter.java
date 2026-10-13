```java
package pl.mgodyn.asciiart.converter;

/**
 * Converts image pixel data (as RGB integers) into ASCII art characters.
 * <p>
 * This class provides a method to map the brightness of a pixel to a character
 * from a predefined set of ASCII characters that represent varying levels of
 * darkness, from very dark to very bright.
 * </p>
 */
public class Converter {

    /**
     * A string containing ASCII characters ordered from darkest to brightest.
     * The character at index 0 represents the darkest possible pixel, and the
     * last character represents the brightest.
     */
    private static final String ASCII_CHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

    /**
     * The maximum possible brightness value for a pixel (i.e., 255 for standard 8‑bit RGB).
     */
    private static final short MAX_POSSIBLE_BRIGHTNESS = 255;

    /**
     * The proportion used to map a brightness value (0‑255) to an index in the {@link #ASCII_CHARS}
     * string. It is calculated as {@code (ASCII_CHARS.length() - 1) / MAX_POSSIBLE_BRIGHTNESS}.
     */
    private static final double PROPORTION = (double) (ASCII_CHARS.length() - 1) / MAX_POSSIBLE_BRIGHTNESS;

    /**
     * Converts an RGB pixel value to its corresponding ASCII character.
     *
     * @param pixel the 24‑bit RGB pixel value (e.g., {@code 0xRRGGBB})
     * @return the ASCII character that best represents the pixel's brightness
     */
    public char convertPixel2Ascii(int pixel) {
        var brightness = convertRGB2Brightness(pixel);
        return convertBrightness2Ascii(brightness);
    }

    /**
     * Calculates the average brightness from the red, green, and blue components of a pixel.
     *
     * @param pixel the 24‑bit RGB pixel value
     * @return the brightness value in the range 0‑255
     */
    private int convertRGB2Brightness(int pixel) {
        var red = getRedFromPixel(pixel);
        var blue = getBlueFromPixel(pixel);
        var green = getGreenFromPixel(pixel);
        return (red + green + blue) / 3;
    }

    /**
     * Extracts the red component from an RGB pixel.
     *
     * @param pixel the 24‑bit RGB pixel value
     * @return the red component (0‑255)
     */
    private int getRedFromPixel(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    /**
     * Extracts the blue component from an RGB pixel.
     *
     * @param pixel the 24‑bit RGB pixel value
     * @return the blue component (0‑255)
     */
    private int getBlueFromPixel(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    /**
     * Extracts the green component from an RGB pixel.
     *
     * @param pixel the 24‑bit RGB pixel value
     * @return the green component (0‑255)
     */
    private int getGreenFromPixel(int pixel) {
        return pixel & 0xff;
    }

    /**
     * Converts a brightness value (0‑255) to the corresponding ASCII character.
     *
     * @param brightness the brightness value (0‑255)
     * @return the ASCII character representing that brightness
     */
    private char convertBrightness2Ascii(int brightness) {
        int asciiChar = (int) Math.round(brightness * PROPORTION);
        return ASCII_CHARS.charAt(asciiChar);
    }
}
```