```java
package pl.mgodyn.converter;

import org.junit.jupiter.api.Test;
import pl.mgodyn.asciiart.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {

    private final Converter underTest = new Converter();

    /**
     * Tests the convertPixel2Ascii method with the lightest RGB color (255, 255, 255).
     * It asserts that the result is the '$' character.
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
     * Tests the convertPixel2Ascii method with the darkest RGB color (0, 0, 0).
     * It asserts that the result is the '`' character.
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
     * Tests the convertPixel2Ascii method with an average RGB color (128, 128, 128).
     * It asserts that the result is the 'v' character.
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
     * Tests the convertPixel2Ascii method with a custom RGB color (0, 128, 240).
     * It asserts that the result is the 'u' character.
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