```java
/**
 * Provides utility methods for image processing.
 *
 * This class offers functionalities for reading images from files and resizing
 * them based on a specified screen size.
 */
class ImageUtilsTest {

    private static BufferedImage testImage;

    @BeforeAll
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
     * It verifies that the method returns a BufferedImage object with the expected dimensions and RGB value.
     *
     * @param imagePath The path to the image file.
     * @param screenSizeProvider A screen size provider.
     * @return The BufferedImage object read from the image file.
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
     * Tests the behavior when the image is null.
     * It asserts that a NullPointerException is thrown when attempting to read a null image.
     */
    @Test
    void shouldThrowException_whenImageIsNull() {
        // given
        final String imagePath = null;
        // expect
        assertThrows(NullPointerException.class, () -> ImageUtils.readImage(imagePath, new DefaultScreenSizeProvider()));
    }

    /**
     * Tests the resizing functionality with a specific screen size.
     * It verifies that the image is resized correctly based on the provided screen size.
     *
     * @param image The BufferedImage to be resized.
     * @param screenSizeProvider A screen size provider.
     * @return The resized BufferedImage object.
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
     * Tests the resizing functionality with another specific screen size.
     * It verifies that the image is resized correctly based on the provided screen size.
     *
     * @param image The BufferedImage to be resized.
     * @param screenSizeProvider A screen size provider.
     * @return The resized BufferedImage object.
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
     * Tests the resizing functionality with a third specific screen size.
     * It verifies that the image is resized correctly based on the provided screen size.
     *
     * @param image The BufferedImage to be resized.
     * @param screenSizeProvider A screen size provider.
     * @return The resized BufferedImage object.
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