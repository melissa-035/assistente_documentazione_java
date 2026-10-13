```java
package pl.mgodyn.asciiart.io;

/**
 * A class to generate ASCII art by appending characters to a StringBuilder.
 * It provides methods to add characters to a row, create new rows, and print
 * the generated ASCII art to the console.
 */
public class AsciiArtPrinter {

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Appends a character repeated three times to the end of the current line.
     *
     * @param ascii The character to append.
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Appends a newline character to the end of the current line.
     * This creates a new row in the ASCII art.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the current content of the StringBuilder to the console.
     * This effectively displays the generated ASCII art.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current content of the StringBuilder as a String.
     * This allows the caller to retrieve the generated ASCII art as a string
     * for further processing or storage.
     *
     * @return The current content of the StringBuilder.
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```