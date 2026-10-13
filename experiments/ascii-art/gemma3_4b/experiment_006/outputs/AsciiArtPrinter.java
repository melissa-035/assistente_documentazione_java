```java
package pl.mgodyn.asciiart.io;

/**
 * A class for printing ASCII art to the console.
 * This class maintains a StringBuilder to accumulate the ASCII art characters
 * and provides methods to add rows, create new rows, and print the
 * accumulated art to the console.
 */
public class AsciiArtPrinter {

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Adds a row of characters to the ASCII art buffer.
     * The given character is repeated three times to form a row.
     *
     * @param ascii The character to add to the row.
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Appends a newline character to the ASCII art buffer, creating a new row.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the accumulated ASCII art to the console.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current accumulated ASCII art as a string.
     *
     * @return The accumulated ASCII art.
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```