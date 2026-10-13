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
     * Utility class for reading and resizing images intended for ASCII art generation.
     * This class contains static methods to handle file input and dimension scaling.
     */
    public final class ImageUtils() {
    }

    private static final double PROPORTION = 3.175;

    /**
     * Reads an image from the specified file path and resizes it based on the provided screen size provider.
     * <p>
     * If the image file is not found or an I/O error occurs during reading, an error message is printed
     * to {@code System.err} and the application exits with a status code of 1.
     *
     * @param imagePath          the path to the image file to read.
     * @param screenSizeProvider the provider for screen dimensions used to calculate the target width.
     * @return a {@code BufferedImage} resized to fit the screen, or {@code null} if an error occurs
     * (though the application typically exits on error before returning).
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
     * Resizes the provided image to fit the calculated target width derived from the screen size.
     * If the source image width is less than or equal to the target width, the original image is returned unchanged.
     *
     * @param image              the source {@code BufferedImage} to potentially resize.
     * @param screenSizeProvider the provider for screen dimensions used to determine the target width.
     * @return the resized {@code BufferedImage} if resizing was required, or the original image otherwise.
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target width for the image based on the screen size.
     * The target width is computed by dividing the screen width by a fixed proportion constant.
     *
     * @param screenSizeProvider the provider for screen dimensions.
     * @return the calculated target width as an integer.
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```