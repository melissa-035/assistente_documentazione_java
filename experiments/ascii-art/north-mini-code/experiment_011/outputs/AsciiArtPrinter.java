```java
/**
 * Utility class for constructing ASCII art in memory. It uses a {@link StringBuilder}
 * to accumulate characters and newlines, providing methods to add rows, create line breaks,
 * print the result, and retrieve the current buffer content.
 */
package pl.mgodyn.asciiart.io;

public class AsciiArtPrinter {

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Adds a row consisting of three repetitions of the specified ASCII character
     * to the internal buffer.
     *
     * @param ascii the character to be repeated and appended to the buffer
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Appends a newline character to the internal buffer.
     * This moves the cursor to the next line for subsequent additions.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the current contents of the internal buffer to standard output
     * using {@link System#out}.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current state of the internal string buffer as a String.
     *
     * @return the accumulated ASCII art string
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```