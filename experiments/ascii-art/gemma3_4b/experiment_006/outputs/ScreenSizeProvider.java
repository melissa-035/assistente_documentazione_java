```java
/**
 * Provides the screen size for the ASCII art application.
 * This interface defines a single method to retrieve the screen dimensions,
 * allowing the application to adapt its output to the user's display.
 */
public interface ScreenSizeProvider {

    /**
     * Returns the screen size as a Dimension object.
     * The Dimension object represents the width and height of the screen
     * in pixels.
     *
     * @return a Dimension object representing the screen size.
     */
    Dimension getScreenSize();
}
```