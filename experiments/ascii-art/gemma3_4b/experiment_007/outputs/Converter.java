```java
package pl.mgodyn.asciiart.converter;

/**
 * Converts a pixel's RGB value into an ASCII character based on brightness.
 * This class provides a method to approximate an image or color with ASCII art.
 */
public class Converter {

    private static final String ASCII_CHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
    private static final short MAX_POSSIBLE_BRIGHTNESS = 255;
    private static final double PROPORTION = (double) (ASCII_CHARS.length() - 1) / MAX_POSSIBLE_BRIGHTNESS;

    /**
     * Converts a pixel's RGB value into an ASCII character based on brightness.
     *
     * @param pixel The RGB value of the pixel.
     * @return The corresponding ASCII character representing the brightness of the pixel.
     */
    public char convertPixel2Ascii(int pixel) {
        var brightness = convertRGB2Brightness(pixel);
        return convertBrightness2Ascii(brightness);
    }

    /**
     * Converts an RGB pixel value into a brightness value.
     * The brightness is calculated by averaging the red, green, and blue components.
     *
     * @param pixel The RGB value of the pixel.
     * @return The calculated brightness value.
     */
    private int convertRGB2Brightness(int pixel) {
        var red = getRedFromPixel(pixel);
        var blue = getBlueFromPixel(pixel);
        var green = getGreenFromPixel(pixel);
        return (red + green + blue) / 3;
    }

    /**
     * Extracts the red component from the given pixel value.
     *
     * @param pixel The pixel value.
     * @return The red component of the pixel.
     */
    private int getRedFromPixel(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    /**
     * Extracts the blue component from the given pixel value.
     *
     * @param pixel The pixel value.
     * @return The blue component of the pixel.
     */
    private int getBlueFromPixel(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    /**
     * Extracts the green component from the given pixel value.
     *
     * @param pixel The pixel value.
     * @return The green component of the pixel.
     */
    private int getGreenFromPixel(int pixel) {
        return pixel & 0xff;
    }

    /**
     * Converts a brightness value into an ASCII character.
     * The brightness value is mapped to an index within the ASCII character string,
     * and the character at that index is returned.
     *
     * @param brightness The brightness value.
     * @return The corresponding ASCII character.
     */
    private char convertBrightness2Ascii(int brightness) {
        int asciiChar = (int) Math.round(brightness * PROPORTION);
        return ASCII_CHARS.charAt(asciiChar);
    }
}
```