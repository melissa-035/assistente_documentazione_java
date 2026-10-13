Here is the Javadoc documentation for the `ImageUtils.java` class, including analysis of the methods and parameters, structured in the standard Javadoc format.

```java
/**
 * A utility class responsible for loading, reading, and resizing images intended for conversion into ASCII art.
 * 
 * <p>This class contains static methods that handle the input and processing of images.
 * It relies on a {@code ScreenSizeProvider} to determine the appropriate dimensions for the image
 * based on the target output (such as terminal or console width). If a critical error occurs (e.g., file
 * not found), the application will terminate immediately to prevent silent failures.</p>
 */
package pl.mgodyn.asciiart.utils;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ImageUtils {

    /**
     * The scaling proportion used to determine the target width of the image relative to the screen width.
     * <p>The image width is calculated as: {@code screenWidth / PROPORTION}.</p>
     */
    private static final double PROPORTION = 3.175;

    /**
     * Constructs a new {@code ImageUtils} instance.
     * <p>This constructor is marked {@code private} to enforce that this class cannot be instantiated,
     * as it contains only static utility methods.</p>
     */
    private ImageUtils() {
    }

    /**
     * Reads an image from the specified file path and resizes it based on the provided screen size.
     * <p>This method performs input validation, loads the image using {@code ImageIO}, and delegates
     * the resizing logic to the {@code resize} method. If the image cannot be read (e.g., file not found
     * or invalid format), an error message is printed to standard error and the application exits with
     * status code 1.</p>
     *
     * @param imagePath           The file path of the image to be read. Must not be {@code null}.
     * @param screenSizeProvider  The provider containing the screen dimensions used for calculating the target width.
     *                            Must not be {@code null}.
     * @return                    The {@code BufferedImage} loaded from the file, resized to fit the calculated target width.
     * @throws NullPointerException if {@code imagePath} or {@code screenSizeProvider} is {@code null}.
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
     * Resizes the given image only if its width exceeds the calculated target width based on the screen size.
     * <p>This method uses the {@code Scalr} library to resize the image while preserving the aspect ratio.
     * If the original image width is smaller than or equal to the target width, the original image is
     * returned unchanged to avoid unnecessary processing.</p>
     *
     * @param image               The source {@code BufferedImage} to be potentially resized.
     * @param screenSizeProvider  The provider containing the screen dimensions used for calculating the target width.
     * @return                    The resized {@code BufferedImage} if resizing was required, otherwise the original {@code BufferedImage}.
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target width for an image based on the current screen width and the predefined proportion constant.
     * <p>The formula applied is: {@code (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION)}.</p>
     *
     * @param screenSizeProvider The provider containing the screen dimensions.
     * @return                   The calculated target width as an integer.
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```