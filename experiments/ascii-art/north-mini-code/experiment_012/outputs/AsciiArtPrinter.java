```java
package pl.mgodyn.asciiart.io;

/**
 * Utility class for constructing and printing ASCII art.
 * It maintains an internal buffer to which characters and rows can be added
 * before printing to standard output.
 */
public class AsciiArtPrinter {

    /**
     * The internal buffer that stores the generated ASCII art characters and newlines.
     */
    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Appends three repetitions of the specified character to the current buffer line.
     * This method is used to construct the horizontal segments of the ASCII art.
     *
     * @param ascii the character to be repeated and appended
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Adds a new line separator to the buffer, effectively moving to the next line
     * of the ASCII art.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the current contents of the buffer to standard output.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current buffer as a String.
     * This is useful for inspecting the generated art or retrieving it programmatically.
     *
     * @return the current state of the ASCII art buffer
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```