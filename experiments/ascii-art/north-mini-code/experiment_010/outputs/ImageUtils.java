```java
package pl.mgodyn.asciiart.utils;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for image processing operations related to ASCII art generation.
 * <p>
 * This class provides methods to read an image from a file and resize it according to the
 * provided screen size, maintaining a specific proportion for optimal ASCII art conversion.
 * </p>
 *
 * @author pl.mgodyn
 * @since 1.0
 */
public final class ImageUtils {

    /**
     * The proportion factor used to calculate the target image width from the screen width.
     * This constant is empirically determined to produce optimal ASCII art dimensions.
     */
    private static final double PROPORTION = 3.175;

    /**
     * Private constructor to prevent instantiation.
     * This utility class cannot be instantiated.
     */
    private ImageUtils() {
    }

    /**
     * Reads an image from the given file path and resizes it according to the screen size provider.
     *
     * <p>If the image cannot be read (e.g., file not found or I/O error), an error message is printed to
     * standard error and the program exits with status code 1.</p>
     *
     * @param imagePath           the path to the image file; must not be {@code null}
     * @param screenSizeProvider  the provider of screen size information used for resizing; must not be {@code null}
     * @return a {@link BufferedImage} resized to the target width; never {@code null} under normal circumstances
     * @see #resize(BufferedImage, ScreenSizeProvider)
     */
    public static BufferedImage readImage(final String imagePath, ScreenSizeProvider screenSizeProvider) {
        Objects.requireNonNull(imagePath);
        Objects.requireNonNull(screenSizeProvider);
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
     * Resizes the given image if its width exceeds the target width calculated from the screen size.
     *
     * @param image               the source image; assumed to be non‑{@code null}
     * @param screenSizeProvider  the provider of screen size information used for calculating the target width
     * @return the original image if its width is less than or equal to the target width,
     *         otherwise a resized version of the image with width equal to the target width
     *         (height is scaled proportionally)
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target width for the image based on the screen width and the constant proportion.
     *
     * @param screenSizeProvider  the provider of screen size information
     * @return the target width as an integer
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```