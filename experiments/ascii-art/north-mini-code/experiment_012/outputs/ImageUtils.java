```java
/**
 * Utility class for reading and processing images to generate ASCII art.
 * This class handles the I/O operations and resizing logic required to convert
 * raster images (like JPG, PNG) into text-based representations suitable for terminal display.
 * It ensures images are scaled to fit the available screen width based on a defined proportion.
 *
 * @since 1.0
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
     * The scaling proportion used to calculate the target width of the image
     * relative to the screen width. Defined as screenWidth / width = proportion.
     * A value of 3.175 is empirically determined for the ASCII art layout.
     */
    private static final double PROPORTION = 3.175;

    /**
     * Private constructor to prevent instantiation of this utility class.
     * All methods are accessed statically via the class name.
     */
    private ImageUtils() {
    }

    /**
     * Reads an image from the specified file path and resizes it based on the provided
     * screen size provider. If the image cannot be read, the application will print
     * an error message to standard error and terminate the program.
     *
     * @param imagePath            The file path to the image. Must not be {@code null}.
     * @param screenSizeProvider   The provider that supplies the current screen dimensions.
     *                             Must not be {@code null}.
     * @return A {@link BufferedImage} resized according to the screen size, or the original
     * image if the file was read successfully but no resizing was required.
     * @throws IOException If an I/O error occurs while reading the image file (e.g., file not found, invalid format).
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
     * Resizes the input image if its width exceeds the target width calculated
     * from the screen size. If the image is already smaller or equal to the target
     * width, the original image is returned.
     *
     * @param image                The source {@link BufferedImage} to process.
     * @param screenSizeProvider   The provider that supplies the current screen dimensions.
     * @return The resized {@link BufferedImage} if resizing was necessary, otherwise
     * the original {@link BufferedImage}.
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target width for the ASCII art image based on the screen
     * width and the predefined {@code PROPORTION} constant.
     * The formula used is: {@code (screenWidth / PROPORTION)}.
     *
     * @param screenSizeProvider The provider containing the screen dimensions.
     * @return The calculated target width as an integer.
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```