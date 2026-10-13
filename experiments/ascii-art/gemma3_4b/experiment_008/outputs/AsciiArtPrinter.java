```java
package pl.mgodyn.asciiart.io;

/**
 * A utility class for printing ASCII art to the console.
 */
public class AsciiArtPrinter {

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Adds a character repeated three times to the current buffer.
     *
     * @param ascii The character to add.
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Appends a newline character to the current buffer, starting a new row.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the contents of the current buffer to the console.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current contents of the buffer as a String.
     *
     * @return The current buffer content.
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```