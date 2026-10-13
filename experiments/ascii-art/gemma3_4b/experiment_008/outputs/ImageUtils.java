```java
package pl.mgodyn.asciiart.utils;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ImageUtils {

    /**
     * The proportion used for resizing images.
     */
    private static final double PROPORTION = 3.175;

    private ImageUtils() {
    }

    /**
     * Reads an image from the specified file path and resizes it.
     *
     * @param imagePath      The path to the image file.
     * @param screenSizeProvider  An object that provides the screen size to determine the resizing factor.
     * @return The resized BufferedImage.
     * @throws NullPointerException if imagePath or screenSizeProvider is null.
     * @throws IOException if there's an error reading the image file.
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
     * Resizes a BufferedImage to a target width based on the provided screen size.
     *
     * @param image           The BufferedImage to resize.
     * @param screenSizeProvider  An object that provides the screen size to determine the resizing factor.
     * @return The resized BufferedImage.  If the original image is smaller than the target width, the original image is returned.
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target image width based on the screen size and the defined proportion.
     *
     * @param screenSizeProvider  An object that provides the screen size to determine the resizing factor.
     * @return The calculated target image width.
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```