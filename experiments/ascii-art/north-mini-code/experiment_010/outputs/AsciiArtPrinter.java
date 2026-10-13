```java
package pl.mgodyn.asciiart.io;

/**
 * This class is responsible for building and printing ASCII art.
 * It uses a {@link StringBuilder} to accumulate lines of ASCII characters.
 * The art is constructed by adding characters (with repetition) and newlines.
 */
public class AsciiArtPrinter {

    /**
     * The internal buffer used to accumulate the ASCII art.
     */
    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Adds a row consisting of three repetitions of the given ASCII character.
     * This method is used to draw horizontal elements of the art (e.g., a line of three identical characters).
     *
     * @param ascii the character to be repeated
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Appends a newline to the buffer, effectively moving to the next line in the ASCII art.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the accumulated ASCII art to standard output.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current content of the buffer as a string.
     *
     * @return the string representation of the ASCII art built so far
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```