```java
package pl.mgodyn.asciiart.io;

/**
 * This class provides a simple way to build and print ASCII art.
 * It uses a StringBuilder to accumulate the ASCII art characters,
 * and offers methods to add rows, create new rows, and print the
 * accumulated art to the console.
 */
public class AsciiArtPrinter {

    /**
     * A private StringBuilder used to efficiently build the ASCII art string.
     * This StringBuilder is final to ensure it is not accidentally modified.
     */
    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Adds a single character to the ASCII art string. The character
     * is repeated three times to form a row of ASCII art.
     *
     * @param ascii The character to add to the ASCII art string.
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Adds a newline character to the ASCII art string, creating a new row.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the entire ASCII art string that has been built up in the
     * StringBuilder to the console.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current ASCII art string as a String.
     *
     * @return The accumulated ASCII art string.
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```