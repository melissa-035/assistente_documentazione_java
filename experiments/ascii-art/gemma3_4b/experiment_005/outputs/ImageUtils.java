```java
package pl.mgodyn.asciiart.utils;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for handling image resizing and reading in ASCII art generation.
 */
public final class ImageUtils {

    /**
     * The proportion used to calculate the target image width.
     */
    private static final double PROPORTION = 3.175;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ImageUtils() {
    }

    /**
     * Reads an image from the specified file path and resizes it based on the screen size.
     *
     * @param imagePath       The path to the image file.
     * @param screenSizeProvider An object providing screen size information for resizing.
     * @return The resized BufferedImage.
     * @throws NullPointerException if imagePath or screenSizeProvider is null.
     * @throws IOException if an error occurs while reading the image file.
     */
    public static BufferedImage readImage(final String imagePath, ScreenSizeProvider screenSizeProvider) {
        Objects.requireNonNull(imagePath);
        Objects.requireNonNull(screenSizeProvider, "ScreenSizeProvider must not be null.");
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return resize(image, screenSizeProvider);
    }

    /**
     * Resizes the given BufferedImage to a target width calculated based on the screen size.
     * If the image is wider than the target width, it is resized to the target width.
     * Otherwise, the original image is returned.
     *
     * @param image            The BufferedImage to resize.
     * @param screenSizeProvider An object providing screen size information for resizing.
     * @return The resized BufferedImage.
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target image width based on the screen size and the defined proportion.
     *
     * @param screenSizeProvider An object providing screen size information.
     * @return The calculated target image width.
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```