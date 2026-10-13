```java
package pl.mgodyn.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {

    private final Converter underTest = new Converter();

    /**
     * Tests the conversion of a pixel with the lightest RGB values to an ASCII character.
     *
     * @param pixel The RGB pixel value to convert.
     * @return The corresponding ASCII character.
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
     * Tests the conversion of a pixel with the darkest RGB values to an ASCII character.
     *
     * @param pixel The RGB pixel value to convert.
     * @return The corresponding ASCII character.
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
     * Tests the conversion of a pixel with average RGB values to an ASCII character.
     *
     * @param pixel The RGB pixel value to convert.
     * @return The corresponding ASCII character.
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
     * Tests the conversion of a pixel with custom RGB values to an ASCII character.
     *
     * @param pixel The RGB pixel value to convert.
     * @return The corresponding ASCII character.
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