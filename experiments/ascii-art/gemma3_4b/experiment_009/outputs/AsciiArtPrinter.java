```java
package pl.mgodyn.asciiart.io;

/**
 * A utility class for printing ASCII art to the console.
 * This class maintains a string builder to efficiently construct the ASCII art representation.
 */
public class AsciiArtPrinter {

    private final StringBuilder stringBuilder = new StringBuilder();

    /**
     * Adds a single character repeated three times to the string builder, representing a row of ASCII art.
     *
     * @param ascii The character to be added. It will be repeated three times.
     */
    public void add2Row(char ascii) {
        var toAppend = String.valueOf(ascii).repeat(3);
        stringBuilder.append(toAppend);
    }

    /**
     * Appends a newline character to the string builder, creating a new row in the ASCII art.
     */
    public void createNewRow() {
        stringBuilder.append("\n");
    }

    /**
     * Prints the current contents of the string builder to the console.
     */
    public void print() {
        System.out.println(stringBuilder);
    }

    /**
     * Returns the current contents of the string builder as a string.
     *
     * @return The string representation of the ASCII art.
     */
    public String getCurrentBuffer() {
        return stringBuilder.toString();
    }
}
```