```java
package pl.mgodyn.io;

import org.junit.jupiter.api.Test;
import pl.mgodyn.asciiart.io.AsciiArtPrinter;

import static org.junit.jupiter.api.Assertions.*;

class AsciiArtPrinterTest {

    /**
     * Tests the {@link AsciiArtPrinter} class.
     */
    private final AsciiArtPrinter underTest = new AsciiArtPrinter();

    /**
     * Tests the {@link AsciiArtPrinter} class: adding 2 rows with '#' character should result in "###".
     *
     * @param ascii The ASCII character to use for adding rows.
     */
    @Test
    void givenChar_whenAdding2Row_shouldAddIt3Times() {
        // given
        final char ascii = '#';

        // when
        underTest.add2Row(ascii);

        // then
        final var expected = "###";
        assertEquals(expected, underTest.getCurrentBuffer());
    }

    /**
     * Tests the {@link AsciiArtPrinter} class: adding a new row with a newline character.
     */
    @Test
    void givenChar_whenAddingNewRow_shouldHaveNewRow() {
        // given
        // when
        underTest.createNewRow();

        // then
        var expected = "\n";
        assertEquals(expected, underTest.getCurrentBuffer());
    }
}
```