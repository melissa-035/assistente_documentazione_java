```java
/**
 * Provides utility methods for working with images, including reading, resizing, and calculating target image widths.
 * This class offers convenient methods for handling image transformations and adapting image sizes to screen resolutions.
 */
public final class ImageUtils {

    private static final double PROPORTION = 3.175;

    private ImageUtils() {
    }

    /**
     * Reads an image from the specified file path and resizes it to a target width based on the provided screen size.
     *
     * @param imagePath      The path to the image file.
     * @param screenSizeProvider A provider of screen size information used to calculate the target image width.
     * @return The resized BufferedImage.
     * @throws NullPointerException if imagePath or screenSizeProvider is null.
     * @throws IOException if an error occurs while reading the image.
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
     * If the image's original width is greater than the target width, the image is resized to the target width.
     * Otherwise, the original image is returned.
     *
     * @param image The BufferedImage to resize.
     * @param screenSizeProvider A provider of screen size information used to calculate the target image width.
     * @return The resized BufferedImage.
     */
    public static BufferedImage resize(BufferedImage image, ScreenSizeProvider screenSizeProvider) {
        var targetWidth = calculateTargetImageWidth(screenSizeProvider);
        return image.getWidth() > targetWidth ? Scalr.resize(image, targetWidth) : image;
    }

    /**
     * Calculates the target image width based on the provided screen size and the PROPORTION constant.
     *
     * @param screenSizeProvider A provider of screen size information.
     * @return The calculated target image width.
     */
    private static int calculateTargetImageWidth(ScreenSizeProvider screenSizeProvider) {
        return (int) (screenSizeProvider.getScreenSize().getWidth() / PROPORTION);
    }
}
```