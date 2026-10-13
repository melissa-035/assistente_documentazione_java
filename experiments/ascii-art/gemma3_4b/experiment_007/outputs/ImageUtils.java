```java
package pl.mgodyn.asciiart.utils;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ImageUtils {

    private static final double PROPORTION = 3.175;

    private ImageUtils() {
    }

    /**
     * Reads an image from the specified file path and resizes it based on the screen size.
     * This method handles potential IOException during image reading and exits the program if an error occurs.
     *
     * @param imagePath The path to the image file.
     * @param screenSizeProvider An object that provides the screen size for resizing.
     * @return The resized BufferedImage.
     * @throws IllegalArgumentException if either imagePath or screenSizeProvider is null.
     * @see ScreenSizeProvider
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
     * Resizes the given BufferedImage to the target width calculated based on the screen size.
     * If the original image width is greater than the target width, it resizes the image using Scalr.
     * Otherwise, it returns the original image.
     *
     * @param image The BufferedImage to resize.
     * @param screenSizeProvider An object that provides the screen size for resizing.
     * @return The resized BufferedImage.
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target image width based on the screen size and the defined proportion.
     *
     * @param screenSizeProvider An object that provides the screen size for calculating the target width.
     * @return The calculated target image width.
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```