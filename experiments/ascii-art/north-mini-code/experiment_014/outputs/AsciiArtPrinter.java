```java
package pl.mgodyn.asciiart.io;

/**
 * AsciiArtPrinter is a utility class for building and printing ASCII art.
 * It uses an internal {@link StringBuilder} to accumulate characters and newlines.
 * The art is constructed row by row, where each row is composed of groups of three identical characters.
 * <p>
 * Example usage:
 * <pre>
 * AsciiArtPrinter printer = new AsciiArtPrinter();
 * printer.add2Row('A'); // adds "AAA"
 * printer.add2Row('B'); // adds "BBB"
 * printer.createNewRow(); // adds newline
 * printer.print(); // prints "AAABBB"
 * </pre>
 */
public class AsciiArtPrinter {

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Appends a row segment consisting of three copies of the specified character to the current line.
     * This method does not add a newline; subsequent calls to this method will append to the same line.
     *
     * @param ascii the character to be repeated three times and appended to the buffer
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Appends a newline character to the buffer, effectively ending the current line and starting a new one.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the entire ASCII art stored in the buffer to standard output.
     * After printing, the buffer remains unchanged (the art can be retrieved via {@link #getCurrentBuffer()}).
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current content of the buffer as a string.
     *
     * @return the accumulated ASCII art as a {@link String}
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```