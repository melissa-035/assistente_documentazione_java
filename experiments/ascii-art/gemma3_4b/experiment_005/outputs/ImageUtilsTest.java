```java
package pl.mgodyn.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.mgodyn.asciiart.utils.DefaultScreenSizeProvider;
import pl.mgodyn.asciiart.utils.ImageUtils;
import pl.mgodyn.asciiart.utils.ScreenSizeProvider;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ImageUtils class. This class provides utility functions for reading and resizing images.
 */
class ImageUtilsTest {

    private static BufferedImage testImage;

    @BeforeAll
    /**
     * Initializes the test image.  This method reads the "ascii-pineapple-700x467.jpg" image from the specified file path.
     * If the file cannot be read, a RuntimeException is thrown to signal the error.
     */
    static void beforeAll() {
        try {
            testImage = ImageIO.read(new File("src/test/resources/ascii-pineapple-700x467.jpg"));
        } catch (IOException e) {
            System.err.println("Could not read test file!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the readImage method with a specific image path and screen size provider.
     * It verifies that the returned BufferedImage object has the correct width, height, and RGB value
     * based on the provided screen size.
     */
    @Test
    void givenImagePath_whenImageReaderIsInvoked_thenReturnsBufferedImageObject() {
        // given
        final String imagePath = "src/test/resources/ascii-pineapple-322x215.jpg";
        ScreenSizeProvider screenSizeProviderMock = mock(ScreenSizeProvider.class);
        when(screenSizeProviderMock.getScreenSize())
                .thenReturn(new Dimension(3840, 2160));

        // when
        var actual = ImageUtils.readImage(imagePath, screenSizeProviderMock);

        // then
        final var expectedHeight = 215;
        final var expectedWidth = 322;
        final var expectedRGB = -16747309;
        assertAll("Should return proper values with appropriate width and height and RGB",
                () -> assertEquals(expectedWidth, actual.getWidth()),
                () -> assertEquals(expectedHeight, actual.getHeight()),
                () -> assertEquals(expectedRGB, actual.getRGB(0, 0))
        );
    }

    /**
     * Tests the readImage method when a null image path is provided.
     * It asserts that a NullPointerException is thrown.
     */
    @Test
    void shouldThrowException_whenImageIsNull() {
        // given
        final String imagePath = null;
        // expect
        assertThrows(NullPointerException.class, () -> ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider()));
    }

    /**
     * Tests the resize method with different screen size resolutions.
     * It verifies that the resized image has the same width as the original image.
     */
    @Test
    void given3840x2160Resolution_whenResizing_thenShouldResizeImage() {
        // given
        ScreenSizeProvider screenSizeProviderMock = mock(ScreenSizeProvider.class);
        Dimension screenSize = new Dimension(3840, 2160);
        when(screenSizeProviderMock.getScreenSize())
                .thenReturn(screenSize);

        // when
        var actual = ImageUtils.resize(testImage, screenSizeProviderMock);

        // then
        var expectedWidth = testImage.getWidth();
        assertEquals(expectedWidth, actual.getWidth());
    }

    /**
     * Tests the resize method with another screen size resolution (2560x1440).
     * It verifies that the resized image has the same width as the original image.
     */
    @Test
    void given2560x1440Resolution_whenResizing_thenShouldResizeImage() {
        // given
        ScreenSizeProvider screenSizeProviderMock = mock(ScreenSizeProvider.class);
        Dimension screenSize = new Dimension(2560, 1440);
        when(screenSizeProviderMock.getScreenSize())
                .thenReturn(screenSize);

        // when
        var actual = ImageUtils.resize(testImage, screenSizeProviderMock);

        // then
        var expectedWidth = testImage.getWidth();
        assertEquals(expectedWidth, actual.getWidth());
    }

    /**
     * Tests the resize method with another screen size resolution (1366x768).
     * It verifies that the resized image has the expected width.
     */
    @Test
    void given1366x768Resolution_whenResizing_thenShouldResizeImage() {
        // given
        ScreenSizeProvider screenSizeProviderMock = mock(ScreenSizeProvider.class);
        Dimension screenSize = new Dimension(1366, 768);
        when(screenSizeProviderMock.getScreenSize())
                .thenReturn(screenSize);

        // when
        var actual = ImageUtils.resize(testImage, screenSizeProviderMock);

        // then
        var expectedWidth = 430;
        assertEquals(expectedWidth, actual.getWidth());
    }
}
```