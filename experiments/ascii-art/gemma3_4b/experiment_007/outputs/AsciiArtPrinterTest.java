```java
package pl.mgodyn.io;

import org.junit.jupiter.api.Test;
import pl.mgodyn.asciiart.io.AsciiArtPrinter;

import static org.junit.jupiter.api.Assertions.*;

class AsciiArtPrinterTest {

    private final AsciiArtPrinter underTest = new AsciiArtPrinter();

    /**
     * Tests the {@link AsciiArtPrinter} class.
     *
     * This test suite verifies the functionality of the AsciiArtPrinter,
     * specifically focusing on the 'add2Row' and 'createNewRow' methods.
     * It ensures that adding a character to a 2 row buffer increases the
     * number of characters in the buffer by three times, and that
     * creating a new row adds a newline character to the current buffer.
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