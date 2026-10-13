```java
package pl.mgodyn.converter;

import org.junit.jupiter.api.Test;
import pl.mgodyn.asciiart.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Converter class. This class contains several JUnit tests
 * to verify the functionality of the convertPixel2Ascii method.
 */
class ConverterTest {

    /**
     * A private instance of the Converter class used for testing.
     */
    private final Converter underTest = new Converter();

    /**
     * Test case that checks if the lightest RGB pixel is correctly converted to ASCII character.
     * The lightest RGB pixel is defined as (255, 255, 255) which represents white.
     * The expected ASCII character for white is '$'.
     */
    @Test
    void givenLightestRGB_whenConvertToAscii_thenReturnsChar() {
        // given
        final int pixel = 16777215; // red = 255, green = 255, blue = 255

        // when
        final var actual = underTest.convertPixel2Ascii(pixel);

        // then
        final char expected = '$';
        assertEquals(expected, actual);
    }

    /**
     * Test case that checks if the darkest RGB pixel is correctly converted to ASCII character.
     * The darkest RGB pixel is defined as (0, 0, 0) which represents black.
     * The expected ASCII character for black is '`'.
     */
    @Test
    void givenDarkestRGB_whenConvertToAscii_thenReturnsChar() {
        // given
        final int pixel = 0; // red = 0, green = 0, blue = 0

        // when
        final var actual = underTest.convertPixel2Ascii(pixel);

        // then
        final char expected = '`';
        assertEquals(expected, actual);
    }

    /**
     * Test case that checks if an average RGB pixel is correctly converted to ASCII character.
     * The average RGB pixel is defined as (128, 128, 128) which represents gray.
     * The expected ASCII character for gray is 'v'.
     */
    @Test
    void givenAverageRGB_whenConvertToAscii_thenReturnsChar() {
        // given
        final int pixel = 8421504; // red = 128, green = 128, blue = 128

        // when
        final var actual = underTest.convertPixel2Ascii(pixel);

        // then
        final char expected = 'v';
        assertEquals(expected, actual);
    }

    /**
     * Test case that checks if a custom RGB pixel is correctly converted to ASCII character.
     * The custom RGB pixel is defined as (0, 128, 240) which represents a specific color.
     * The expected ASCII character for this color is 'u'.
     */
    @Test
    void givenCustomRGB_whenConvertToAscii_thenReturnsChar() {
        // given
        final int pixel = 33008; // red = 0, green = 128, blue = 240

        // when
        final var actual = underTest.convertPixel2Ascii(pixel);

        // then
        final char expected = 'u';
        assertEquals(expected, actual);
    }
}
```